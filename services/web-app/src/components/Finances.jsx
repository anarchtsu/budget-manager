import React, {useEffect, useState} from "react";
import axios from '../api/Axios';
import MyModal from "../UI/MyModal/MyModal";

class FinanceOperation {
    id;
    date;
    currencyCode;
    amount;
    type;
    period;
    categoryName;


    constructor(id, date, currencyCode, amount, type, period, categoryName) {
        this.id = id;
        this.date = date;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.type = type;
        this.period = period;
        this.categoryName = categoryName;
    }
}

const deleteById = (id) => {

}

const Finances = () => {
    const [finances, setFinances] = useState([]);
    const [isModalVisible, setModelVisible] = useState(false);
    const [currentFinanceOperation, setCurrentFinanceOperation] = useState(null);

    useEffect(() => {
        // (async () => {
        //     await axios.get("/api/v1/finance-operations").then(r => {
        //         const financeOperations = r.data.map(x => new FinanceOperation(x.id, x.date, x.currencyCode, x.amount, x.type, x.period, x.categoryName));
        //         console.log(financeOperations)
        //         setFinances(financeOperations);
        //     })
        // })();
        axios.get("/api/v1/finance-operations").then(r => {
            const financeOperations = r.data.map(x => new FinanceOperation(x.id, x.date, x.currencyCode, x.amount, x.type, x.period, x.categoryName));
            console.log(financeOperations)
            setFinances(financeOperations);
        })
    }, [])

    const openModal = (e, f) => {
        e.preventDefault()
        setCurrentFinanceOperation(f)
        setModelVisible(true)
    }

    const closeModal = (e) => {
        e.preventDefault()
        setCurrentFinanceOperation(null)
        setModelVisible(false)
    }

    const modal = isModalVisible &&
        <MyModal
            financeOperation={currentFinanceOperation}
            onClose={closeModal}
        />

    return (
        <div>
            {/* todo Add button */}
            <div className="finances">
                <div className="finances__item">
                    <p>INCOMES</p>
                    <div className="list">
                        {finances.filter(f => f.type === 'INCOME').map(f =>
                            <div className="finances-item" onClick={e => openModal(e, f)}>
                                <p className="finances-item__name">{f.categoryName}</p>
                                <p className="finances-item__value">{f.currencyCode + " " + f.amount + " PER " + f.period}</p>
                            </div>
                        )}
                    </div>
                </div>

                <div className="finances__item">
                    <p>EXPENSES</p>
                    <div className="list">
                        {finances.filter(f => f.type === 'EXPENSE').map(f =>
                            <div className="finances-item" onClick={e => openModal(e, f)}>
                                <p className="finances-item__name">{f.categoryName}</p>
                                <p className="finances-item__value">{f.currencyCode + " " + f.amount + " PER " + f.period}</p>
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
