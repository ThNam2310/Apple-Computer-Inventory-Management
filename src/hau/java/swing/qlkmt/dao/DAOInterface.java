package hau.java.swing.qlkmt.dao;

import java.util.ArrayList;

public interface DAOInterface<T> {

    public int insert(T t);

    public int update(T t);

    public int delete(T t);

    public ArrayList<T> selectAll();

    public T selectById(String t);
}
