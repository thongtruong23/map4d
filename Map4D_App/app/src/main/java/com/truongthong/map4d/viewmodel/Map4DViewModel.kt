package com.truongthong.map4d.viewmodel

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongthong.map4d.api.RetrofitInstance
import com.truongthong.map4d.model.route.RouteLocation
import com.truongthong.map4d.model.search.MapResponse
import com.truongthong.map4d.util.Constants.Companion.API_KEY
import kotlinx.coroutines.launch
import retrofit2.Response


class Map4DViewModel : ViewModel() {

    val searchLocation : MutableLiveData<Response<MapResponse>> = MutableLiveData()
    val routeLocation : MutableLiveData<Response<RouteLocation>> = MutableLiveData()

     fun getSearchLocation(searchQuery: String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.searchLocation(searchQuery,API_KEY)
            try {
                searchLocation.value = response
            }catch (e:NetworkErrorException){

            }
        }
    }

    fun getRouteLocation(origin: String, destination: String, mode: String){
        viewModelScope.launch {
            val response = RetrofitInstance.api.getRoute(API_KEY, origin, destination, mode)
            try {
                routeLocation.value = response
            }
            catch (e : NetworkErrorException){

            }
        }

    }

}