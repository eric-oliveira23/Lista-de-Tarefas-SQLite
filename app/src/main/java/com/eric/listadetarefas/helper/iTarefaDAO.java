package com.eric.listadetarefas.helper;

import com.eric.listadetarefas.model.Atividade;

import java.util.List;

public interface iTarefaDAO {

    public boolean salvar(Atividade atividade);
    public boolean atualizar(Atividade atividade);
    public boolean deletar(Atividade atividade);
    public boolean deletarTodos();
    public int contar();
    public List<Atividade> listar();

}
