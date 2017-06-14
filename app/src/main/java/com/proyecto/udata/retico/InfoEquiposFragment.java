package com.proyecto.udata.retico;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by James on 4/6/2017.
 */

public class InfoEquiposFragment extends Fragment implements View.OnClickListener{
    ImageButton btnBackSpaceMostrarEquipos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_info_equipos, container, false);
        btnBackSpaceMostrarEquipos = (ImageButton)view.findViewById(R.id.btnBackSpaceMostrarEquipos);
        btnBackSpaceMostrarEquipos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackSpaceMostrarEquipos:
                startActivity(new Intent(v.getContext(),ListaEquipos.class));
                break;
        }
    }
}
