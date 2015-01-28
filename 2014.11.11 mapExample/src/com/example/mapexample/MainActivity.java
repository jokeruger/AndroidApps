package com.example.mapexample;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.maps.android.clustering.ClusterManager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	static final LatLng MANKATO = new LatLng(46.15, -94.4);
	private GoogleMap map;
	private ClusterManager<MyItem> mClusterManager;
	double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        
    	//Move the camera instantly to minnesota with a zoom of 15.
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(MANKATO, 7));

    	// Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null); 
    	
    	// Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(this, map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraChangeListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

    }

    public double randRange(double min, double max){
    	return Math.random() * (max-min) + min;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void resetMap() {
    	mClusterManager.clearItems();
    	map.clear();
    }
    
    public void populate1400() {
		int i= 0;
		while (i<1400){
			lat = randRange(43.5,48);
			lng = randRange(-96.4,-92.85);
			MyItem marker = new MyItem(lat, lng);
			mClusterManager.addItem(marker);
//			map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
			i++;
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(MANKATO, 6));
    	map.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null); 
    }
    
    public void populate14000() {
		int i= 0;
		while (i<14000){
			lat = randRange(43.5,48);
			lng = randRange(-96.4,-92.85);
			MyItem marker = new MyItem(lat, lng);
			mClusterManager.addItem(marker);
//			map.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
			i++;
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(MANKATO, 3));
    	map.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null); 
    }

    public void populatePhotos() {
    	String[] photos = getResources().getStringArray(R.array.photos);
		for (int i=0;i<=6315;i++) {
				if (i==5192) 	//  to bypass <item>44.589700 N, 93.102300 W</item>
					continue;	//  WHY???
//java.lang.RuntimeException: 
//Unable to start activity ...MainActivity: 
//java.lang.NumberFormatException: 
//Invalid double: "93.10 3"

			String latS = photos[i].split(",")[0];
			String lngS = photos[i].split(",")[1];
			
			lat = Double.parseDouble(latS.replace(" N", ""));
			
			if (lngS.endsWith("E"))
				lng = Double.parseDouble(lngS.replace(" E", ""));
			else
				lng = -(Double.parseDouble(lngS.replace(" W", "")));
			
			MyItem marker = new MyItem(lat, lng);
			mClusterManager.addItem(marker);
		}
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.7, -89.2), 3));
    	map.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null); 
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            resetMap();
        }
        if (id == R.id.action_1400) {
            resetMap();
            populate1400();
        }
        if (id == R.id.action_14000) {
        	resetMap();
        	populate14000();
        }
        if (id == R.id.action_photos) {
            resetMap();
            populatePhotos();
        }
        return super.onOptionsItemSelected(item);
    }
}
