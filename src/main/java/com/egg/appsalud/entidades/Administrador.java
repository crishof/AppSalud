package com.egg.appsalud.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {

}
