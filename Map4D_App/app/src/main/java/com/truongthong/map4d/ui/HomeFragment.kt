package com.truongthong.map4d.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialFadeThrough
import com.truongthong.map4d.R
import com.truongthong.map4d.adapter.MapSearchAdapter
import com.truongthong.map4d.model.search.MapLocation
import com.truongthong.map4d.viewmodel.Map4DViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import vn.map4d.map.annotations.MFMarkerOptions
import vn.map4d.map.camera.MFCameraUpdateFactory
import vn.map4d.map.core.Map4D
import vn.map4d.map.core.OnMapReadyCallback
import vn.map4d.types.MFLocationCoordinate


class HomeFragment : Fragment(), OnMapReadyCallback, MapSearchAdapter.OnMapItemClickListener {

    private var map4D: Map4D? = null
    private var mapAdapter = MapSearchAdapter(arrayListOf(), this)
    private lateinit var mapViewModel: Map4DViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        permissionMap()

        mapView.getMapAsync(this)

        setupRecyclerView()

        clickRouteListener()

        mapViewModel = ViewModelProvider(this).get(Map4DViewModel::class.java)

        mapViewModel.searchLocation.observe(viewLifecycleOwner, Observer { reponse ->
            if (reponse.isSuccessful) {
                reponse.body()?.let { locationRespone ->
                    mapAdapter.setLocationData(locationRespone.result)

                }

            }


        })

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                rv_listSuggest.setVisibility(View.VISIBLE);
                if (query != null) {
                    mapViewModel.getSearchLocation(query)

                }

                mapAdapter.filter.filter(query)
                return false
            }
        })
    }


    private fun clickRouteListener() {
        btn_route.setOnClickListener {
            val routeLocationFragment: Fragment = RouteLocationFragment()
            val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!

            transaction.replace(R.id.frameLayout, routeLocationFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setupRecyclerView() {
        mapAdapter = MapSearchAdapter(arrayListOf(), this)
        rv_listSuggest.apply {
            adapter = mapAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    override fun onMapReady(map: Map4D?) {
        map4D = map
        getCurrentLocation()
        mfUISeting()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        enterTransition = MaterialFadeThrough()
        return view
    }


    fun getCurrentLocation() {
        var location = MFLocationCoordinate(15.9828, 108.2370)
        map4D?.addMarker(
            MFMarkerOptions().position(location).title("Mai Dang Chon")
                .snippet("UBND Phuong Hoa Quy")
        )
        map4D?.moveCamera(MFCameraUpdateFactory.newCoordinate(location))
    }

    //cap quyen su dung
    fun permissionMap() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 100
            )
        }

    }

    fun mfUISeting() {
        map4D?.uiSettings?.isMyLocationButtonEnabled = true
        map4D?.uiSettings?.isRotateGesturesEnabled = true
        map4D?.uiSettings?.setAllGesturesEnabled(true)
        map4D?.setTiltGesturesEnabled(true)

    }

    //xu ly su kien khi click vao item recyclerview
    override fun onItemClick(suggestList: MapLocation, position: Int) {

        val lat = suggestList.location.lat
        val lng = suggestList.location.lng

        val mLocation = MFLocationCoordinate(lat, lng)

        map4D?.addMarker(
            MFMarkerOptions().position(mLocation).title(suggestList.name)
                .snippet(suggestList.address)
        )
//        map4D?.moveCamera(MFCameraUpdateFactory.newCoordinateZoom(mLocation,19.0))
        map4D?.animateCamera(MFCameraUpdateFactory.newCoordinate(mLocation))

        rv_listSuggest.setVisibility(View.INVISIBLE);


    }

}

