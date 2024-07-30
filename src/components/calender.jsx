import '@mobiscroll/react/dist/css/mobiscroll.min.css';
import { Datepicker, Page, setOptions } from '@mobiscroll/react';

setOptions({
  theme: 'ios',
  themeVariant: 'light'
});

function DatePicker() {
  return (
    <Page>
      <Datepicker inputComponent="input" inputProps={{ placeholder: 'Please Select...' }} />
      <Datepicker inputStyle="outline" label="Date" labelStyle="stacked" placeholder="Please Select..." />
      <Datepicker display="inline" />
    </Page>
  );
}

export default DatePicker;