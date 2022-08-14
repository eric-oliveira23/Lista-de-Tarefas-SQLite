package com.eric.listadetarefas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.eric.listadetarefas.R;
import com.eric.listadetarefas.adapter.AtividadeAdapter;
import com.eric.listadetarefas.helper.RecyclerItemClickListener;
import com.eric.listadetarefas.helper.TarefaDAO;
import com.eric.listadetarefas.model.Atividade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerAtividades;
    private AtividadeAdapter atividadeAdapter;
    private List<Atividade> listaAtividades = new ArrayList<>();
    private TextView txvAmount;
    private Atividade atividadeSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerAtividades = findViewById(R.id.recyclerAtividades);
        txvAmount = findViewById(R.id.txvAmount);

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

                           }

                           @Override
                           public void onLongItemClick(View view, int position) {

                               atividadeSelecionada = listaAtividades.get(position);

                               AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                               alertDialog.setTitle("Confirmar Exclusão?");
                               alertDialog.setMessage("Deseja mesmo excluir a tarefa "+atividadeSelecionada.getNomeTarefa()+"?");

                               alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {

                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                                        if (tarefaDAO.deletar(atividadeSelecionada)){
                                            ListarTarefas();

                                            Toast.makeText(MainActivity.this, "Tarefa "
                                                    +atividadeSelecionada.getNomeTarefa()+
                                                    " excluída com sucesso", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(MainActivity.this,
                                                    "Erro ao excluir tarefa",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                   }
                               });

                               alertDialog.setNegativeButton("Não", null);

                               alertDialog.create().show();
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

        AtualizarNumeroColunas();
    }

    @Override
    protected void onStart() {
        this.ListarTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemlimparlista:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Você tem certeza?");
                alertDialog.setMessage("Todas as tarefas serão apagadas!");
                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                        tarefaDAO.deletarTodos();
                        ListarTarefas();

                        Toast.makeText(getApplicationContext(),
                                "As tarefas foram apagadas",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                alertDialog.setNegativeButton("Não", null);
                alertDialog.create().show();
                break;

            case R.id.itemadicionar:

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AtualizarNumeroColunas(){

        //mostra quantidade de colunas
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        txvAmount.setText(""+tarefaDAO.contar());

    }
}