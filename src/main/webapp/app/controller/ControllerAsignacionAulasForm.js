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
            'formAsignacionAulas' :
            {
                dirtychange : function(form, isDirty)
                {
                    if (isDirty)
                    {
                        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('button[name=close]').setText('Tancar sense guardar');
                    }
                    else
                    {
                        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('button[name=close]').setText('Tancar');
                    }
                }
            }
        });
    },

    cerrarFormulario : function()
    {
        var formulario = Ext.ComponentQuery.query('formAsignacionAulas')[0];
        var me = this;

        if (formulario.getForm().isDirty())
        {
            Ext.Msg.confirm('Dades sense guardar', 'Tens dades sense guardar en el event, estàs segur de voler tancar la edició?', function(btn, text)
            {
                if (btn == 'yes')
                {
                    me.logicaCerrarFormulario();
                }
            });
        }
        else
        {
            this.logicaCerrarFormulario();
        }
    },

    logicaCerrarFormulario : function()
    {
        var panelCalendario = Ext.ComponentQuery.query('panelCalendario')[0];
        panelCalendario.hideAsignarAulaView();
        var formulario = Ext.ComponentQuery.query('formAsignacionAulas')[0];
        formulario.fireEvent('eventasigaaulacancel');
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

        formulario.down('combobox[name=tipoAccion]').setValue('T');

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
            formulario.down('combobox[name=aulaPlanificacion]').setValue(rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name]);
            formulario.down('button[name=borrarAsignacion]').show();
        }
        else
        {
            formulario.down('combobox[name=aulaPlanificacion]').clearValue();
            formulario.down('button[name=borrarAsignacion]').hide();
        }

        formulario.getForm().setValues(formulario.getForm().getValues());
    },

    borrarAsignacionAula : function()
    {
        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('combobox').clearValue();
    },
    
    guardarDatosFormulario : function()
    {
        
    }

});