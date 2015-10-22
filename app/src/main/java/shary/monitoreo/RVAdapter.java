package shary.monitoreo;

/**
 * Created by Shary on 21/10/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecetaViewHolder> {
    private List<DescripcionPaciente> items;
    RecyclerView recyclerView;
    View context;
    public String pacienteNemeAux = "";
    public List<String> listado;

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
            Toast.makeText(itemView.getContext(), items.get(getPosition()).getName() + "", Toast.LENGTH_SHORT).show();
        }

    }

}
