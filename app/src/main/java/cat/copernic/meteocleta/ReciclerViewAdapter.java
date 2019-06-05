package cat.copernic.meteocleta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReciclerViewAdapter extends RecyclerView.Adapter<ReciclerViewAdapter.ClimaViewHolder> {

    private Context context;
    private List<clima> climaList;


    public ReciclerViewAdapter(Context context, List<clima> climaList) {
        this.context = context;
        this.climaList = climaList;
    }
    class ClimaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTemperature, tvhumidity, tvPressure, tvWind, tvRain;

        public ClimaViewHolder(View itemView) {
            super(itemView);
            tvTemperature = itemView.findViewById(R.id.tvTemperatura);
            tvhumidity = itemView.findViewById(R.id.tvHumedad);
            tvPressure = itemView.findViewById(R.id.tvPresion);
            tvWind = itemView.findViewById(R.id.tvViento);
            tvRain = itemView.findViewById(R.id.tvLluvia);
        }
    }



    @Override
    public ClimaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_clima, null);
        return new ClimaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClimaViewHolder holder, int position) {
        clima clima = climaList.get(position);
        holder.tvTemperature.setText(String.valueOf(clima.getTemperatura())+ " Cº");
        holder.tvWind.setText(String.valueOf(clima.getVelocitatVent())+ " km/h");
        holder.tvPressure.setText(String.valueOf(clima.getPressioAtmosferica()) + " pA");
        holder.tvhumidity.setText(String.valueOf(clima.getHumitat())+ " %");
        holder.tvRain.setText("Now is " + clima.getPluja());

    }

    @Override
    public int getItemCount() {
        return climaList.size();
    }


}