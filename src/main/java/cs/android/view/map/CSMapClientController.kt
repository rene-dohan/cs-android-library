package cs.android.view.map

import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import cs.android.extensions.execute
import cs.android.extensions.view.frame
import cs.android.extensions.view.layoutMatchParent
import cs.android.viewbase.CSViewController

open class CSMapClientController<V : View>(parent: CSViewController<V>, private val mapFrameId: Int,
                                           private val mapController: CSMapController) :
        CSViewController<V>(parent) {

    var lastLocation: LatLng? = null
    var lastZoom: Float? = null

    override fun onViewShowing() {
        super.onViewShowing()
        (mapController.view!!.parent as? ViewGroup)?.removeView(mapController.view)
        whileShowing(mapController.onMapAvailable { map ->
            map.clear()
            map.setOnMapLongClickListener(null)
            map.setOnInfoWindowClickListener(null)
        })
        frame(mapFrameId).addView(mapController.view!!, layoutMatchParent())
        whileShowing(mapController.onCameraMoveStopped.execute { map ->
            lastLocation = map.cameraPosition.target
            lastZoom = map.cameraPosition.zoom
        })
        lastLocation?.let { latLng -> mapController.animateCamera(latLng, lastZoom!!) }
    }
}