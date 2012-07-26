Ext.define('HOR.controller.ControllerConfiguracion',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreConfiguracion', 'StoreHoras' ],
    model : [ 'Configuracion' ],
    refs : [
    {
        selector : 'panelHorarios filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector: 'selectorIntervaloHorario',
        ref: 'selectorIntervaloHorario'
    }],
    init : function()
    {
        this.control(
        {
            'panelHorarios filtroGrupos button[name=intervaloHorario]' :
            {
                click : this.showIntervaloHorario
            },
            'selectorIntervaloHorario button[action=save]' :
            {
                click : this.guardarConfiguracion
            },
            'selectorIntervaloHorario button[action=cancel]' :
            {
                click : function(button)
                {
                    button.up("selectorIntervaloHorario").destroy();
                }
            },
            'panelHorarios filtroGrupos combobox[name=grupo]' :
            {
                select : function(combo) {
                    combo.up('filtroGrupos').down('button[name=intervaloHorario]').setVisible(true);
                }
            }
        });
    },

    guardarConfiguracion : function()
    {
        var estudioId = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var cursoId = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestreId = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupoId = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();
        var horaInicio = this.getSelectorIntervaloHorario().down('combobox[name=horaInicio]').getValue();
        var horaFin = this.getSelectorIntervaloHorario().down('combobox[name=horaFin]').getValue();

        var storeConfiguracion = this.getStoreConfiguracionStore();

        var record = Ext.create("HOR.model.Configuracion",
        {
            estudioId : estudioId,
            cursoId : cursoId,
            semestreId : semestreId,
            grupoId : grupoId,
            horaInicio : horaInicio,
            horaFin : horaFin
        });
        storeConfiguracion.add(record);
        storeConfiguracion.sync(
        {
            failure : function()
            {
                storeConfiguracion.rejectChanges();
            },
            success: function() {
                Ext.ComponentQuery.query("selectorIntervaloHorario")[0].destroy();
            }
        });
    },

    showIntervaloHorario : function()
    {
        Ext.create('HOR.view.horarios.SelectorIntervaloHorario').show();
        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();
        var semestre = this.getFiltroGrupos().down('combobox[name=semestre]').getValue();
        var grupo = this.getFiltroGrupos().down('combobox[name=grupo]').getValue();

        var storeConfiguracion = this.getStoreConfiguracionStore();

        storeConfiguracion.load(
        {
            params :
            {
                estudioId : estudio,
                cursoId : curso,
                semestreId : semestre,
                grupoId : grupo
            },
            scope : this,
            callback : function(records, operation, success)
            {
                if (success)
                {
                    var record = records[0];
                    var fechaInicio = record.get('horaInicio');
                    var fechaFin = record.get('horaFin');

                    var inicio = Ext.Date.parse(fechaInicio, 'd/m/Y H:i:s', true);
                    var fin = Ext.Date.parse(fechaFin, 'd/m/Y H:i:s', true);

                    horaInicio = Ext.Date.format(inicio, "H:i");
                    horaFin = Ext.Date.format(fin, "H:i");

                    this.getSelectorIntervaloHorario().down('combobox[name=horaInicio]').setValue(horaInicio);
                    this.getSelectorIntervaloHorario().down('combobox[name=horaFin]').setValue(horaFin);

                }

            }
        });
    }
});
