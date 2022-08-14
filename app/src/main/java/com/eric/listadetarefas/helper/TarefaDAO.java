package com.eric.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eric.listadetarefas.model.Atividade;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements iTarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {

        DbHelper dbHelper = new DbHelper(context);

        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("tarefa", atividade.getNomeTarefa());

        try {
            escreve.insert(DbHelper.TASK_TABLE, null, cv);
            Log.i("INFO DB","TAREFA SALVA COM SUCESSO");
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Atividade atividade) {

        ContentValues cv = new ContentValues();
        cv.put("tarefa", atividade.getNomeTarefa());

        try {
            String[] args = {atividade.getId().toString()};
            escreve.update(DbHelper.TASK_TABLE, cv, "id = ?", args);
            Log.i("INFO DB","TAREFA ATUALIZADA COM SUCESSO ");
        }
        catch (Exception e){
            Log.i("INFO DB","ERRO AO ATUALIZAR TAREFA "+e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    public boolean deletar(Atividade atividade) {

        try {
            String[] args = {atividade.getId().toString()};
            escreve.delete(DbHelper.TASK_TABLE, "id=?", args);
            Log.i("INFO DB","TAREFA DELETADA COM SUCESSO ");
        }
        catch (Exception e){
            Log.i("INFO DB","ERRO AO DELETAR TAREFA "+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletarTodos() {

        try {
            SQLiteDatabase sqLiteDatabase = this.escreve;
            String sql = "DELETE FROM " + DbHelper.TASK_TABLE + ";";
            sqLiteDatabase.execSQL(sql);

        } catch (Exception e) {
            Log.i("INFO DB","ERRO AO DELETAR TODOS "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Atividade> listar() {

        List<Atividade> atividades = new ArrayList<>();

        String sql = "SELECT * FROM "+DbHelper.TASK_TABLE+";";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Atividade atividade = new Atividade();

            Long id = c.getLong(c.getColumnIndexOrThrow("id") );
            String nomeTarefa = c.getString(c.getColumnIndexOrThrow("tarefa"));

            atividade.setId(id);
            atividade.setNomeTarefa(nomeTarefa);

            atividades.add(atividade);

        }

        return atividades;
    }


    public int contar() {

        int result = 0;

        String sql = "SELECT COUNT(*) FROM "+DbHelper.TASK_TABLE+";";
        Cursor c = le.rawQuery(sql, null);

        if (c.moveToFirst()){
            result = c.getInt(0);
        }
        c.close();

        return result;
    }
}
