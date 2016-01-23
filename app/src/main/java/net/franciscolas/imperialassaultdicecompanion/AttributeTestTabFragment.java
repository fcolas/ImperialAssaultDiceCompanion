package net.franciscolas.imperialassaultdicecompanion;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttributeTestTabFragment extends Fragment implements Button.OnClickListener {

    public AttributeTestTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.attribute_test_tab, container, false);

        Button addButton = (Button) view.findViewById(R.id.add_attribute_test_fragment);
        addButton.setOnClickListener(this);

        if (view.findViewById(R.id.attribute_test_tab_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return view;
            }

            // Create a new Fragment to be placed in the activity layout
            AttributeTestFragment firstFragment = new AttributeTestFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getActivity().getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.attribute_test_tab_fragment_container, firstFragment)
                    .commit();
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_attribute_test_fragment:
                addAttributeTestFragment();
        }
    }

    public void addAttributeTestFragment() {
        // Create fragment and give it an argument specifying the article it should show
        AttributeTestFragment newFragment = new AttributeTestFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.attribute_test_tab_fragment_container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

}
