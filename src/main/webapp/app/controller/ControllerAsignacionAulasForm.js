Ext.define('HOR.controller.ControllerAsignacionAulasForm',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreAulasAsignadas' ],
    refs : [
    {
        selector : 'formAsignacionAulas',
        ref : 'formAsignacionAulas'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    } ],

    init : function()
    {
        this.control(
        {
            'formAsignacionAulas button[name=close]' :
            {
                click : this.cerrarFormulario
            },
            'panelCalendario' :
            {
                eventasignaaula : function(cal, rec)
                {
                    this.cargarDatosFormulario(rec);
                }
            },
            'formAsignacionAulas button[name=borrarAsignacion]' :
            {
                click : this.borrarAsignacionAula
            },
        });
    },

    cerrarFormulario : function()
    {
        var panelCalendario = Ext.ComponentQuery.query('panelCalendario')[0];
        panelCalendario.hideAsignarAulaView();
    },

    cargarDatosFormulario : function(rec)
    {
        var formulario = Ext.ComponentQuery.query('formAsignacionAulas')[0];

        formulario.down('displayfield[name=event]').setValue(rec.data[Extensible.calendar.data.EventMappings.Title.name]);
        formulario.down('hiddenfield[name=eventId]').setValue(rec.data[Extensible.calendar.data.EventMappings.EventId.name]);

        if (rec.data[Extensible.calendar.data.EventMappings.Comunes.name])
        {
            formulario.down('displayfield[name=comunes]').setValue(rec.data[Extensible.calendar.data.EventMappings.Comunes.name]);
            formulario.down('displayfield[name=comunes]').show();
        }
        else
        {
            formulario.down('displayfield[name=comunes]').hide();
        }

        var store = this.getStoreAulasAsignadasStore();
        var estudioId = formulario.up("panelHorarios").down("filtroGrupos combobox[name=estudio]").getValue();
        var semestreId = formulario.up("panelHorarios").down("filtroGrupos combobox[name=semestre]").getValue();

        store.load(
        {
            url : '/hor/rest/aula/estudio/' + estudioId,
            params :
            {
                semestreId : semestreId
            }
        });

        if (rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name])
        {
            console.log('Entras??? ' + rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name]);
            formulario.down('combobox[name=aulaPlanificacion]').setValue(rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name]);
            formulario.down('button[name=borrarAsignacion]').show();
        }
        else
        {
            formulario.down('button[name=borrarAsignacion]').hide();
        }
    },

    borrarAsignacionAula : function()
    {
        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('combobox').clearValue();
    }
});