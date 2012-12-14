Ext.define('HOR.controller.ControllerAsignacionAulasForm',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreAulasAsignadas', 'StoreEventos' ],
    refs : [
    {
        selector : 'formAsignacionAulas',
        ref : 'formAsignacionAulas'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    } ],

    dirty : false,

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
            'formAsignacionAulas button[name=save]' :
            {
                click : this.guardarDatosFormulario
            },
            'formAsignacionAulas combobox[name=aulaPlanificacion]' :
            {
                change : function()
                {
                    this.setIsFormularioDirty(Ext.ComponentQuery.query('formAsignacionAulas')[0]);
                    var boton = Ext.ComponentQuery.query('formAsignacionAulas button[name=borrarAsignacion]')[0];
                    if (Ext.ComponentQuery.query('formAsignacionAulas combobox[name=aulaPlanificacion]')[0].getValue() != null)
                    {
                        boton.show();
                    }
                    else
                    {
                        boton.hide();
                    }
                }
            },
            'formAsignacionAulas combobox[name=tipoAccion]' :
            {
                change : function()
                {
                    this.setIsFormularioDirty(Ext.ComponentQuery.query('formAsignacionAulas')[0]);
                }
            }
        });
    },

    cerrarFormulario : function()
    {
        var me = this;

        if (this.dirty)
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
        var me = this;
        var formulario = Ext.ComponentQuery.query('formAsignacionAulas')[0];
        formulario.getForm().reset();

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
            },
            callback : function(records, operation, success)
            {
                if (success && rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name])
                {
                    formulario.down('combobox[name=aulaPlanificacion]').setValue(rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name]);
                    formulario.down('combobox[name=aulaPlanificacion]').lastValue = rec.data[Extensible.calendar.data.EventMappings.AulaPlanificacionId.name];
                    formulario.down('button[name=borrarAsignacion]').show();
                }
                else
                {
                    formulario.down('combobox[name=aulaPlanificacion]').clearValue();
                    formulario.down('button[name=borrarAsignacion]').hide();
                }

                me.resetForm(formulario);
            }
        });
    },

    borrarAsignacionAula : function()
    {
        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('combobox').clearValue();
    },

    guardarDatosFormulario : function()
    {
        var me = this;
        var formulario = Ext.ComponentQuery.query('formAsignacionAulas')[0];
        var store = formulario.up("panelCalendario").getEventStore();

        var eventoId = formulario.down('hiddenfield[name=eventId]').getValue();
        var aulaId = formulario.down('combobox[name=aulaPlanificacion]').getValue();
        var tipoAccion = formulario.down('combobox[name=tipoAccion]').getValue();

        Ext.Ajax.request(
        {
            url : '/hor/rest/calendario/eventos/aula/evento/' + eventoId,
            method : 'PUT',
            params :
            {
                aulaId : aulaId,
                tipoAccion : tipoAccion
            },
            success : function(response)
            {
                me.resetForm(formulario);
                var eventos = Ext.JSON.decode(response.responseText).data;

                for ( var i = 0; i < eventos.length; i++)
                {
                    var record = store.getById(eventos[i].id.toString());
                    record.set(Extensible.calendar.data.EventMappings.AulaPlanificacionId.name, aulaId);
                }
            },
            failure : function(response)
            {
                if (response.responseXML)
                {
                    var msgList = response.responseXML.getElementsByTagName("msg");

                    if (msgList && msgList[0] && msgList[0].firstChild)
                    {
                        Ext.MessageBox.show(
                        {
                            title : 'Server error',
                            msg : msgList[0].firstChild.nodeValue,
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK
                        });
                    }
                }
            }
        });
    },

    setIsFormularioDirty : function(formulario)
    {
        var comboAulas = formulario.down('combobox[name=aulaPlanificacion]');
        var comboAccion = formulario.down('combobox[name=tipoAccion]');

        if (comboAulas.getValue() != comboAulas.originalValue || comboAccion.getValue() != comboAccion.originalValue)
        {
            this.dirty = true;
            Ext.ComponentQuery.query('formAsignacionAulas')[0].down('button[name=close]').setText('Tancar sense guardar');
        }
        else
        {
            Ext.ComponentQuery.query('formAsignacionAulas')[0].down('button[name=close]').setText('Tancar');
            this.dirty = false;
        }
    },

    resetForm : function(formulario)
    {
        var comboAulas = formulario.down('combobox[name=aulaPlanificacion]');
        var comboAccion = formulario.down('combobox[name=tipoAccion]');

        comboAulas.originalValue = comboAulas.getValue();
        comboAccion.originalValue = comboAccion.getValue();

        Ext.ComponentQuery.query('formAsignacionAulas')[0].down('button[name=close]').setText('Tancar');
        this.dirty = false;
    }
});