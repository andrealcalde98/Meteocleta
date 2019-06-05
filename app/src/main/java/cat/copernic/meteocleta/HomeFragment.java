package cat.copernic.meteocleta;

        import android.os.Build;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 1/23/2018.
 */

public class HomeFragment extends Fragment {
    private static final String url_clima = "http://192.168.1.130/Myapp/MyApp.php";

    List<clima>climaList;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        Log.d("1","infla?");
        return inflater.inflate(R.layout.fragment_home, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("2","entra en el recyclerView");
        recyclerView = view.findViewById(R.id.reciclerClima);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        climaList = new ArrayList<>();
        loadClima();
    }

    private void loadClima() {
        Log.d("3","entra en el load clima");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Internet", String.valueOf(NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted()));
        }

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url_clima,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        clima adaptador = new clima();

                        Log.d("4","entra en el json");
                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject json = null;
                                json = array.getJSONObject(i);

                                adaptador.setTemperatura(json.getInt("temperatura"));
                                adaptador.setVelocitatVent(json.getInt("velocitatVent"));
                                adaptador.setPressioAtmosferica(json.getInt("pressioAtmosferica"));
                                adaptador.setHumitat(json.getInt("humetat"));
                                adaptador.setPluja(json.getString("pluja"));


                                Log.d("ADAPTADOR_INFO", json.getInt("temperatura")+"");
                            }
                            ReciclerViewAdapter adapter = new ReciclerViewAdapter(getActivity(), climaList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        climaList.add(adaptador);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                        Log.d("4","volley"+ error.getMessage());
                    }
                });Log.d("4","sale en el on response");

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
}
