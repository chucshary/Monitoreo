package shary.monitoreo;

/**
 * Created by Shary on 21/10/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Configuraciones.Ubicacion;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecetaViewHolder> {
    private List<DescripcionPaciente> items;
    private View context;
    private String ids;
    private SharedPreferences sharedPreferences;
    private String[] _id;
    private Ubicacion ubicacion;

    public RVAdapter(List<DescripcionPaciente> items, View context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecetaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview, viewGroup, false);
        return new RecetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecetaViewHolder holder, int position) {
        Picasso.with(context.getContext()).load(items.get(position).getPhoto()).into(holder.pacientePhoto);
        holder.pacienteName.setText(items.get(position).getName());
        holder.pacienteCategory.setText(items.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecetaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView pacientePhoto;
        TextView pacienteName;
        TextView pacienteCategory;

        public RecetaViewHolder(View itemView) {
            super(itemView);
            pacienteName = (TextView) itemView.findViewById(R.id.nombre);
            pacienteCategory = (TextView) itemView.findViewById(R.id.categoria);
            pacientePhoto = (ImageView) itemView.findViewById(R.id.imagen);
            itemView.setTag(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            idPaciente(getPosition());
            sharedPreferences = context.getContext().getSharedPreferences("LATILONG", Context.MODE_PRIVATE);
            String _lat = sharedPreferences.getString("Latitud", "");
            String _long = sharedPreferences.getString("Longitud", "");
            String _datos = sharedPreferences.getString("Datos", "");
            //Toast.makeText(itemView.getContext(), "POS " + getPosition() + "  " + items.get(getPosition()).getName() + "", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(v.getContext(), MapsActivity.class);
            intent.putExtra("Latitud", _lat);
            intent.putExtra("Longitud", _long);
            intent.putExtra("Datos", _datos);
            intent.putExtra("Paciente", items.get(getPosition()).getName());
            v.getContext().startActivity(intent);
            //((AppCompatActivity)v.getContext()).finish();
        }

    }

    public void idPaciente(int pos) {
        int paciente = 0;
        String datos;
        sharedPreferences = context.getContext().getSharedPreferences("NOTIFICACION", Context.MODE_PRIVATE);
        ids = sharedPreferences.getString("ids", "");
        _id = ids.split("/");
        paciente = Integer.parseInt(_id[pos]);
        ubicacion = new Ubicacion(context.getContext());
        ubicacion.getUbicacion(paciente);
    }

}
