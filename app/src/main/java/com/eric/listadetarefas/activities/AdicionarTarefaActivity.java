package com.eric.listadetarefas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eric.listadetarefas.R;
import com.eric.listadetarefas.helper.TarefaDAO;
import com.eric.listadetarefas.model.Atividade;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText textTarefa;
    private Snackbar snackbar;
    private ConstraintLayout constraintLayout;
    private Atividade atividadeAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        textTarefa = findViewById(R.id.textTarefa);

        //recuperar atividade, caso seja edição
        atividadeAtual = (Atividade) getIntent().getSerializableExtra("atividadeSelecionada");

       if (atividadeAtual != null) {
           textTarefa.setText(atividadeAtual.getNomeTarefa());
       }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemsalvar:

                String tarefa = textTarefa.getText().toString();
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefa.isEmpty()) {
                    Toast.makeText(this, "Você não pode salvar uma tarefa vazia", Toast.LENGTH_SHORT).show();
                }else {
                    if (atividadeAtual != null) {

                        Atividade atividade = new Atividade();
                        atividade.setNomeTarefa(tarefa);
                        atividade.setId(atividadeAtual.getId());

                        //atualizar no banco de dados
                            tarefaDAO.atualizar(atividade);
                            finish();
                            Toast.makeText(this, "Sucesso ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Atividade atividade = new Atividade();
                        atividade.setNomeTarefa(tarefa);
                        tarefaDAO.salvar(atividade);

                        Toast.makeText(this, "Tarefa salva com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                    break;
        }

        return super.onOptionsItemSelected(item);
    }
}