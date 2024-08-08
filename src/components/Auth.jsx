import { useState, useEffect } from 'react';

const useSessionStorage = (key) => {
  const [value, setValue] = useState(() => {
    const storedValue = sessionStorage.getItem(key);
    try {
      return storedValue ? JSON.parse(storedValue) : null;
    } catch (error) {
      return storedValue;
    }
  });

  useEffect(() => {
    sessionStorage.setItem(key, value);
  }, [key, value]);

  return [value, setValue];
};

export default useSessionStorage;
