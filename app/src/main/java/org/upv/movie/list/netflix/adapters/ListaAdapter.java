package org.upv.movie.list.netflix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.upv.movie.list.netflix.utils.ListasVector;
import org.upv.movie.list.netflix.R;

/**
 * Created by Lionel on 07/11/2017.
 */

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private ListasVector listasVector;

    public ListaAdapter(ListasVector listasVector) {
        this.listasVector = listasVector;
    }

    public void setSource(ListasVector listasVector) {
        this.listasVector = listasVector;
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento_lista, viewGroup, false);
        return new ListaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(listasVector.elemento(i).getIcono());
        viewHolder.titulo.setText(listasVector.elemento(i).getTitulo());
        viewHolder.descripcion.setText(listasVector.elemento(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listasVector.tamanyo();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        ImageView imagen;
        TextView titulo;
        TextView descripcion;

        ListaViewHolder(View v) {
            super(v);
            imagen = v.findViewById(R.id.imagen);
            titulo = v.findViewById(R.id.titulo);
            descripcion = v.findViewById(R.id.descripcion);
        }
    }
}
