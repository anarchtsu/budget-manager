import React, {useEffect, useState} from "react";
import axios from '../api/Axios';
import MyModal from "../UI/MyModal/MyModal";

class FinanceOperation {
    id;
    date;
    currencyId;
    currencyCode;
    amount;
    type;
    period;
    categoryName;

    constructor(id, date, currencyId, currencyCode, amount, type, period, categoryName) {
        this.id = id;
        this.date = date;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.type = type;
        this.period = period;
        this.categoryName = categoryName;
    }
}

const FinanceOperationUrl = '/api/v1/finance-operations'

const Finances = () => {
    const [finances, setFinances] = useState([])
    const [isModalVisible, setModelVisible] = useState(false)
    const [currentFinanceOperation, setCurrentFinanceOperation] = useState(null)

    const refreshData = () => {
        axios.get(FinanceOperationUrl).then(r => {
            const financeOperations = r.data.map(x => new FinanceOperation(
                x.id,
                x.date,
                x.currencyId,
                x.currencyCode,
                x.amount,
                x.type,
                x.period,
                x.categoryName
            ));
            setFinances(financeOperations)
        })
    }

    useEffect(() => refreshData(), [])

    const openModal = (e, f) => {
        e.preventDefault()
        setCurrentFinanceOperation(f)
        setModelVisible(true)
    }

    const onModalClose = () => {
        setCurrentFinanceOperation(null)
        setModelVisible(false)
        refreshData()
    }

    const modal = isModalVisible &&
        <MyModal
            financeOperation={currentFinanceOperation}
            onClose={onModalClose}
            url={FinanceOperationUrl}
        />

    return (
        <div>
            {/* todo Add button */}
            <div className="finances">
                <div className="finances__item">
                    <p>INCOMES</p>
                    <div className="list">
                        {finances.filter(f => f.type === 'INCOME').map(f =>
                            <div key={f.id} className="finances-item" onClick={e => openModal(e, f)}>
                                <p className="finances-item__name">{f.categoryName}</p>
                                <p className="finances-item__value">{f.currencyCode + " " + f.amount + " PER " + f.period}</p>
                                <button onClick={e => {
                                    e.preventDefault()
                                    e.stopPropagation()
                                    axios.delete(FinanceOperationUrl + '/' + f.id).then(r => refreshData())
                                }}>X</button>
                            </div>
                        )}
                    </div>
                </div>
                <button className="" onClick={e => openModal(e, null)}>+</button>
                <div className="finances__item">
                    <p>EXPENSES</p>
                    <div className="list">
                        {finances.filter(f => f.type === 'EXPENSE').map(f =>
                            <div key={f.id} className="finances-item" onClick={e => openModal(e, f)}>
                                <p className="finances-item__name">{f.categoryName}</p>
                                <p className="finances-item__value">{f.currencyCode + " " + f.amount + " PER " + f.period}</p>
                                <button onClick={e => {
                                    e.preventDefault()
                                    e.stopPropagation()
                                    axios.delete(FinanceOperationUrl + '/' + f.id).then(r => refreshData())
                                }}>X</button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            {/* todo Calendar */}
            {modal}
        </div>
    );
}

export default Finances;
