package es.uji.apps.hor;

import java.util.Comparator;

import es.uji.apps.hor.model.Asignatura;

public class AsignaturasComparator implements Comparator<Asignatura>
{
    @Override
    public int compare(Asignatura asignatura1, Asignatura asignatura2)
    {
        return asignatura1.getId().compareTo(asignatura2.getId());
    }
}
