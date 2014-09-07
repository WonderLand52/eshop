package com.rudenkoInc.eshop.dao.externalTxDao;


import com.rudenkoInc.eshop.dao.WorkToDoInterface.UnitOfWork;
import com.rudenkoInc.eshop.dao.exceptions.DaoException;


public interface TransactionsManager {
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> workToDo) throws E;
}
