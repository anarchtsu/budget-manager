import React, {useEffect, useState} from "react";
import axios from '../api/Axios';
import MyModal from "../UI/MyModal/MyModal";
import FinancesList from "./FinancesList";

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

const url = '/api/v1/finance-operations'

const Finances = () => {
    const [finances, setFinances] = useState([])
    const [isModalVisible, setModelVisible] = useState(false)
    const [currentFinanceOperation, setCurrentFinanceOperation] = useState(null)

    const refreshData = () => {
        axios.get(url).then(r => {
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
            url={url}
        />

    return (
        <div>
            <div className="finances">
                <FinancesList
                    type={'INCOME'}
                    financeOperations={finances}
                    openModal={openModal}
                    url={url}
                    refreshData={refreshData}
                />
                <button className="" onClick={e => openModal(e, null)}>+</button>
                <FinancesList
                    type={'EXPENSE'}
                    financeOperations={finances}
                    openModal={openModal}
                    url={url}
                    refreshData={refreshData}
                />
            </div>
            {/* todo Calendar */}
            {modal}
        </div>
    );
}

export default Finances;
