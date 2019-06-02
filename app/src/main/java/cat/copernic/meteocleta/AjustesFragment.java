package cat.copernic.meteocleta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class AjustesFragment extends Fragment {
    Button privacidad, desconecta, idioma;
    private FirebaseAuth mAuth;
    private String LANG_CURRENT = "en";
    private FragmentManager fragmentManager;
    private Fragment fragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_ajustes, null);
    }

    public void onViewCreated(View view, final Bundle savedInstanceState) {

        fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, "Ajustes").commitAllowingStateLoss();
        privacidad = view.findViewById(R.id.btn_privacidad);
        privacidad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://rehabilitat.cat/blog/privacypolicymeteocleta");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        desconecta = view.findViewById(R.id.btn_desconecta);
        mAuth = FirebaseAuth.getInstance();
        desconecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        idioma = view.findViewById(R.id.btn_idioma);
        idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIdioma();
            }
        });
    }

    private void showIdioma() {

        final CharSequence[] opciones = {"English", "Español", "Català"};
        final android.support.v7.app.AlertDialog.Builder idiomas = new android.support.v7.app.AlertDialog.Builder(getActivity());
        idiomas.setTitle("Select Lenguage");
        idiomas.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("English")) {
                    if (LANG_CURRENT.equals("es") || LANG_CURRENT.equals("ca")) {
                        changeLang(getContext(), "en");
                        updateResources(getContext(), "en");
                    }
                    /*Fragment frg = null;
                    frg = getFragmentManager().findFragmentByTag("Settings");
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();*/
                    //startActivity(new Intent(getContext(), AjustesFragment.this));
                } else if
                (opciones[i].equals("Español")) {
                    if (LANG_CURRENT.equals("en") || LANG_CURRENT.equals("ca")) {
                        changeLang(getContext(), "es");
                        updateResources(getContext(), "es");
                    }
                } else if
                (opciones[i].equals("Català")) {
                    if (LANG_CURRENT.equals("es") || LANG_CURRENT.equals("en")) {
                        changeLang(getContext(), "ca");
                        updateResources(getContext(), "ca");
                    }
                }
                    /*if (opciones[i].equals("Carrega Imatge") || opciones[i].equals("Sube Foto") || opciones[i].equals("Add Photo")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //intent.setType("image/");
                        //startActivityForResult(intent.createChooser(intent,":)"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }*/
            }
        });
        idiomas.show();

    }

    public void changeLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", lang);
        editor.apply();
    }

    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        LANG_CURRENT = preferences.getString("Language", "en");

        attachBaseContext(MyContextWrapper.wrap(newBase, LANG_CURRENT));
    }

    private static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }
}