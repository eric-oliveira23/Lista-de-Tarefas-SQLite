package com.eric.listadetarefas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.eric.listadetarefas.R;
import com.eric.listadetarefas.adapter.AtividadeAdapter;
import com.eric.listadetarefas.helper.DbHelper;
import com.eric.listadetarefas.helper.RecyclerItemClickListener;
import com.eric.listadetarefas.helper.TarefaDAO;
import com.eric.listadetarefas.model.Atividade;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerAtividades;
    private AtividadeAdapter atividadeAdapter;
    private List<Atividade> listaAtividades = new ArrayList<>();
    private TextView txvAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerAtividades = findViewById(R.id.recyclerAtividades);
        txvAmount = findViewById(R.id.txvAmount);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

            }
        });

        recyclerAtividades.addOnItemTouchListener(
               new RecyclerItemClickListener(
                       getApplicationContext(),
                       recyclerAtividades,
                       new RecyclerItemClickListener.OnItemClickListener() {
                           @Override
                           public void onItemClick(View view, int position) {

                               //recuperar tarefa selecionada
                               Atividade atividade = listaAtividades.get(position);

                               //enviar para a outra activity
                               Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                               intent.putExtra("atividadeSelecionada", atividade);

                               startActivity(intent);

                               Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onLongItemClick(View view, int position) {

                           }

                           @Override
                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                           }
                       }
               )
        );
    }

    public void ListarTarefas(){

       //listar atividades
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaAtividades = tarefaDAO.listar();

        //configurar recycler view

        //configurar adapter
        atividadeAdapter = new AtividadeAdapter(listaAtividades);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerAtividades.setLayoutManager(layoutManager);
        recyclerAtividades.setHasFixedSize(true);
        recyclerAtividades.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL));
        recyclerAtividades.setAdapter(atividadeAdapter);
    }

    @Override
    protected void onStart() {
        this.ListarTarefas();
        super.onStart();

        //show column amount
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        txvAmount.setText(""+tarefaDAO.contar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemeditar:
                Toast.makeText(this,
                        "editar teste",
                        Toast.LENGTH_SHORT)
                        .show();
                break;

            case R.id.itemsalvar:
                Toast.makeText(this,
                        "salvar teste",
                        Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}