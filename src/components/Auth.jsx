import { useState, useEffect } from 'react';

const useSessionStorage = (key, initialValue = null) => {
  const [value, setValue] = useState(() => {
    const storedValue = sessionStorage.getItem(key);
    try {
      return storedValue ? JSON.parse(storedValue) : initialValue;
    } catch (error) {
      return storedValue || initialValue;
    }
  });

  useEffect(() => {
    if (value !== null) {
      sessionStorage.setItem(key, JSON.stringify(value));
    } else {
      sessionStorage.removeItem(key); // Remove item if value is null
    }
  }, [key, value]);

  return [value, setValue];
};

export default useSessionStorage;
