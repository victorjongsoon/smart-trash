package sg.edu.np.team2.smarttrash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class NearMeFragment extends Fragment {

    MapView mMapView;
    private View view;
    private GoogleMap googleMap;
    private DatabaseReference databaseReference;
    private String baseData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_nearme, container, false); //pass the correct layout name for the fragment


        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Get Firebase RTDatabase Instance
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bin");

        //Run method to view data from firebase in maps

        firebasePlotData("1");
        firebasePlotData("2");
        firebasePlotData("3");
        firebasePlotData("4");
        firebasePlotData("5");



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void mapBin(final String binUID, final double lat, final double lng, final String title, final long sensorData) {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                String binLoad;
                long loadMeter;

                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( //Method of Fragment
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1
                    );
                }

                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(lat, lng);

                if (sensorData <= 100 | sensorData >= 0) {
                    loadMeter = 100 - sensorData;
                } else {
                    loadMeter = 100;
                }

                if (sensorData <= 100 & sensorData > 60) {
                    binLoad = "Low";
                    googleMap.addMarker(new MarkerOptions().position(location).title(title + " " + binUID).snippet("Bin Status: " + binLoad + " " + loadMeter + "%").icon(BitmapDescriptorFactory.fromResource(R.drawable.binlow)));
                } else if (sensorData <= 60 & sensorData > 20) {
                    binLoad = "Medium";
                    googleMap.addMarker(new MarkerOptions().position(location).title(title + " " + binUID).snippet("Bin Status: " + binLoad + " " + loadMeter + "%").icon(BitmapDescriptorFactory.fromResource(R.drawable.binmed)));
                } else if (sensorData <= 20 & sensorData > -1) {
                    binLoad = "High";
                    googleMap.addMarker(new MarkerOptions().position(location).title(title + " " + binUID).snippet("Bin Status: " + binLoad + " " + loadMeter + "%").icon(BitmapDescriptorFactory.fromResource(R.drawable.binhigh)));
                } else {
                    binLoad = "UNDER MAINTENANCE";
                    googleMap.addMarker(new MarkerOptions().position(location).title(title + " " + binUID).snippet("Bin Status: " + binLoad + " " + loadMeter + "%").icon(BitmapDescriptorFactory.fromResource(R.drawable.binna)));
                }





                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void firebasePlotData(String binUID) {
        databaseReference.child(binUID).child("binData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                baseData = (String) dataSnapshot.getValue();
                String[] elements = baseData.split(",");
                List<String> fixedLenghtList = Arrays.asList(elements);

                mapBin(fixedLenghtList.get(0), Double.parseDouble(fixedLenghtList.get(1)), Double.parseDouble(fixedLenghtList.get(2)), fixedLenghtList.get(3), Long.parseLong(fixedLenghtList.get(4)));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}

