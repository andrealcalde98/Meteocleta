package cat.copernic.meteocleta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 1/23/2018.
 */

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewWeather;
    private ReciclerViewAdapter adapterWeather;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_home, null);
    }
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        recyclerViewWeather = view.findViewById(R.id.reciclerClima);
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterWeather = new ReciclerViewAdapter(obtenerClima());
        recyclerViewWeather.setAdapter(adapterWeather);
    }
    public List<clima> obtenerClima(){
        List<clima> datos = new ArrayList<>();
        datos.add(new clima("28º",R.drawable.clima_nuves));
        datos.add(new clima("60%",R.drawable.clima_nuves));
        datos.add(new clima("1000",R.drawable.clima_nuves));
        datos.add(new clima("poca",R.drawable.clima_nuves));
        datos.add(new clima("50km/h",R.drawable.clima_nuves));




        return datos;
    }
}
