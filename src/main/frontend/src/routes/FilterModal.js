import React, { useState } from 'react';
import "../styles/css/FilterModal.css";

function FilterModal({ isOpen, onClose, filter, setFilter }) {
    const [tempFilter, setTempFilter] = useState(filter);

    const handleApply = () => {
        setFilter(tempFilter);
        onClose();
    };

    const handleClick = sizeValue => {
        setTempFilter({ size: sizeValue });
    };

    const isSelected = sizeValue => {
        return tempFilter.size === sizeValue;
    };

    return (
        isOpen && (
            <div className="filter-modal">
                <h2>사이즈별 리뷰 필터</h2>
                <div className="filter-options">
                    <label>
                        사이즈
                        {["275이상", "270", "265", "260", "255", "250", "245", "240", "235", "230", "225이하", "전체보기"].map((label, index) => {
                            const values = [">=275", "270", "265", "260", "255", "250", "245", "240", "235", "230", "<=225", ""];

                            return (
                                <button
                                    key={index}
                                    className={isSelected(values[index]) ? "selected" : ""}
                                    onClick={() => handleClick(values[index])}
                                >
                                    {label}
                                </button>
                            );
                        })}
                    </label>
                </div>
                <div className="buttons-container">
                    <button onClick={onClose}>닫기</button>
                    <button onClick={handleApply}>적용</button>
                </div>
            </div>
        )
    );
}

export default FilterModal;