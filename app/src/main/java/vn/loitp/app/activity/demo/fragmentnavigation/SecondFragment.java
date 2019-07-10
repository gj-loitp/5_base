package vn.loitp.app.activity.demo.fragmentnavigation;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import loitp.basemaster.R;

public class SecondFragment extends Fragment {

    public SecondFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button button = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                new NavOptions.Builder().setExitAnim(R.anim.fade_out);
                navController.navigate(R.id.action_secondFragment_to_thirdFragment);


            }
        });

    }

    public interface OnFragmentInteractionListener {
        //To do//
        void onFragmentInteraction(Uri uri);
    }
}
