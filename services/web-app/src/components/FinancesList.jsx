import React from 'react';
import axios from "../api/Axios";

const FinancesList = ({type, financeOperations, openModal, url, refreshData}) => {
    return (
        <div className="finances__item">
            <p>{type + 'S'}</p>
            <div className="list">
                {financeOperations.filter(f => f.type === type).map(f =>
                    <div key={f.id} className="finances-item" onClick={e => openModal(e, f)}>
                        <p className="finances-item__name">{f.categoryName}</p>
                        <p className="finances-item__value">{f.currencyCode + " " + f.amount + " PER " + f.period}</p>
                        <button onClick={e => {
                            e.preventDefault()
                            e.stopPropagation()
                            axios.delete(url + '/' + f.id).then(r => refreshData())
                        }}>X</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FinancesList;