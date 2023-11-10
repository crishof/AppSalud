package com.egg.appsalud.controladores;


import com.egg.appsalud.Exception.MiException;
import com.egg.appsalud.entidades.Especialidad;
import com.egg.appsalud.entidades.Profesional;
import com.egg.appsalud.entidades.Usuario;
import com.egg.appsalud.servicios.UsuarioServicio;
import com.egg.appsalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panelAdmin")
public class AdminControlador {
        
    @Autowired
    private ProfesionalServicio cs;

    
    @Autowired
    private UsuarioServicio us;
    
    @GetMapping("/admin")
    public String panelAdmin(ModelMap modelo){
        
        List<Profesional> profesional = cs.listarProfesional();
        modelo.addAttribute("profesional", profesional);
        
    return "PanelAdmin.html";
    }
    
    @GetMapping("/usuarios")
    public String usuarios(ModelMap modelo,ModelMap modelo2){
        
        List<Usuario> usuarios = us.listarUsuario();
        modelo.addAttribute("usuarios", usuarios);
        
         List<Usuario> profesionales = us.listarProfesional();
        modelo2.addAttribute("profesional", profesionales);
    
    return "Usuarios.html";
    }
   
    @GetMapping("/crear/{id}")
    public String crearProfesional(@PathVariable String id,@RequestParam Especialidad especialidad, ModelMap modelo) throws MiException{
        
        try {
            us.crearProfesional(id,especialidad);
            modelo.put("exito","Profesional creado con exito");

        }catch(MiException ex){
            
            modelo.put("error",ex.getMessage());
            return "redirect:/dashboard/usuarios";
            
        }return "redirect:/dashboard/usuarios";
    }
    
    @GetMapping("/profesional")
    public String profesional(ModelMap modelo){
        
        List<Usuario> profesional = us.listarProfesional();
        modelo.addAttribute("profesional", profesional);
        
    return "Usuarios.html";
    }
    
    @PostMapping("/modificar")
    public String modificarProfesional(@RequestParam String id,@RequestParam Boolean activo,@RequestParam Integer sueldoMensual, ModelMap modelo) throws MiException{
        
        try {
            
            us.modificarProfesional(id, sueldoMensual, activo);
            modelo.put("exito","Profesional modificado con exito");

        }catch(MiException ex){
            
            modelo.put("error",ex.getMessage());
            return "redirect:/dashboard/usuarios";
            
        }return "redirect:/dashboard/usuarios";
    }
    
     @GetMapping("/eliminar/{id}")
   public String eliminarUs(@PathVariable String id){
       
       us.eliminarUsuario(id);
       
       return "redirect:/dashboard/usuarios";
}  
}
