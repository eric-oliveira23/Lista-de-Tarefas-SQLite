package com.eric.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eric.listadetarefas.R;
import com.eric.listadetarefas.model.Atividade;

import java.util.List;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.MyViewHolder> {

    private List<Atividade> listaAtividades;

    public AtividadeAdapter(List<Atividade> lista) {
        this.listaAtividades = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_tarefa, parent, false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Atividade atividade = listaAtividades.get(position);
        holder.txtTarefa.setText(atividade.getNomeTarefa());

    }

    @Override
    public int getItemCount() {
        return this.listaAtividades.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTarefa = itemView.findViewById(R.id.txtTarefa);

        }
    }

}
