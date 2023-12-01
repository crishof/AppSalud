package com.egg.appsalud.servicios;

import com.egg.appsalud.entidades.FichaMedica;
import com.egg.appsalud.entidades.Paciente;
import com.egg.appsalud.repositorios.FichaMedicaRepositorio;
import org.springframework.stereotype.Service;


@Service
public class FichaMedicaServicio {

    final
    FichaMedicaRepositorio fichaMedicaRepositorio;

    public FichaMedicaServicio(FichaMedicaRepositorio fichaMedicaRepositorio) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
    }

    public void crearFichaMedica(Paciente paciente){

//        FichaMedica f = new FichaMedica();
//        f.setPaciente(paciente);
//        fichaMedicaRepositorio.save(f);

        FichaMedica fichaMedica = fichaMedicaRepositorio.buscarIdPaciente(paciente.getId());
//
        if ( fichaMedica == null){
        FichaMedica ficha = new FichaMedica();
        ficha.setPaciente(paciente);
        fichaMedicaRepositorio.save(ficha);
        }


        /*
            FichaMedica fichaMedica = buscarPorIdPaciente(paciente.getId());

            if(fichaMedica != null){
                return fichaMedica;
            }else {
                FichaMedica fichaMedica1 = new FichaMedica();
                fichaMedica1.setPaciente(paciente);
                fichaMedicaRepositorio.save(fichaMedica1);
                return fichaMedica1;
            }

         */
    }
    public void modificarFichaMedica(Paciente paciente, String antecedentes, String obraSocial, Long afiliado,
                                 String grupoSanguineo, double altura, double peso) {

        FichaMedica ficha = buscarPorIdPaciente(paciente.getId());
        ficha.setAntecedentes(antecedentes);
        ficha.setObraSocial(obraSocial);
        ficha.setNumeroAfiliado(afiliado);
        ficha.setGrupoSanguineo(grupoSanguineo);
        ficha.setAltura(altura);
        ficha.setPeso(peso);
        fichaMedicaRepositorio.save(ficha);
    }

    public FichaMedica buscarPorIdPaciente(String id){

        FichaMedica fichaMedica= fichaMedicaRepositorio.buscarIdPaciente(id);

//        System.out.println("FICHA: " + fichaMedica.getPaciente().getNombre());

        return fichaMedica;
    }
}
