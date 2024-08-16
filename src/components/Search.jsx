import React, { useState } from 'react';
import axios from 'axios';

const SearchComponent = () => {
  const [inputTypeDate, setInputTypeDate] = useState('text');

  const search = () => {
    const query = document.getElementById('searchInput').value;
    axios.get(`/api/v1/task/search?search=${query}`)
      .then(response => {
        console.log('Search results:', response.data);
      })
      .catch(error => {
        console.error('There was an error with the search!', error);
      });
  };

  return (
    <div className="dashboard-header-container-2">
      <div className="search">
        <input type="text" placeholder="Search" id="searchInput" />
        <button onClick={search} className="search-icon-button">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24" color="#000000" fill="none" className="search-icon">
            <path d="M17.5 17.5L22 22" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M20 11C20 6.02944 15.9706 2 11 2C6.02944 2 2 6.02944 2 11C2 15.9706 6.02944 20 11 20C15.9706 20 20 15.9706 20 11Z" stroke="currentColor" strokeWidth="1.5" strokeLinejoin="round" />
          </svg>
        </button>
      </div>
      <div className="date-filter">
        <input placeholder={inputTypeDate === 'text' ? 'From' : ''} onFocus={() => setInputTypeDate('date')} type={inputTypeDate} />
        <input placeholder={inputTypeDate === 'text' ? 'To' : ''} onFocus={() => setInputTypeDate('date')} type={inputTypeDate} />
      </div>
    </div>
  );
};

export default SearchComponent;
