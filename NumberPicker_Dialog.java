public void showVolumeDialog(View view) 
{
        //Create bundle set initial values and limits
        Bundle bundle = new Bundle();
        bundle.putInt("min", 1);
        bundle.putInt("max", 50);
        bundle.putInt("current", 1);
        
        //Get string value from resource file
        String volTitle = getResources().getString(R.string.volume_dialog_title);
        bundle.putString("dialog_title", volTitle);

        //Instantiate NumberPicker Fragment
        NumberPickerDialogFragment volFragment = new NumberPickerDialogFragment();

        //set bundle to fragment
        volFragment.setArguments(bundle);

        //Inflate dialog
        volFragment.show(getSupportFragmentManager(),"volumePicker");
}

public static class NumberPickerDialogFragment extends DialogFragment implements NumberPicker.OnValueChangeListener
{
    private  int _min, _max, _current;
    private  NumberPicker.OnValueChangeListener _listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)  {

        //get values stored in bundle
        savedInstanceState = this.getArguments();

        //set variables to bundle values
        _min = savedInstanceState.getInt("min");
        _max = savedInstanceState.getInt("max");
        _current = savedInstanceState.getInt("current");
        _listener = this;

        //must have a xml file with a numberPicker element
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.number_picker, null);

        //NumberPicker set up
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(_max);
        numberPicker.setMinValue(_min);
        numberPicker.setValue(_current);
        numberPicker.setOnValueChangedListener(_listener);

        //Set up AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(savedInstanceState.getString("dialog_title"));
        builder.setView(view);
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Cancel dialog
                NumberPickerDialogFragment.this.getDialog().cancel();
            }
        });
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //clear current focus, must be done before retrieving value
                numberPicker.clearFocus();
        
                //If user typed in value get new value
                _current = numberPicker.getValue();
            
                //Display current number as a test
                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(_current),Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        //update current value
        _current = newVal;
    }
}
