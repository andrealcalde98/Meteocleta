package cat.copernic.meteocleta;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReciclerViewAdapter extends RecyclerView.Adapter<ReciclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView weather;
        ImageView pictureWeather;
        public ViewHolder(View itemView){
            super(itemView);
            weather =(TextView)itemView.findViewById(R.id.tvTemperatura);
            pictureWeather =(ImageView)itemView.findViewById(R.id.imgClima);
        }
    }
    public List<clima>weatherList;
    public ReciclerViewAdapter(List<clima>weatherList){
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_clima,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.weather.setText(weatherList.get(position).getTemperature());
        holder.pictureWeather.setImageResource(weatherList.get(position).getPhotoWeather());

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
