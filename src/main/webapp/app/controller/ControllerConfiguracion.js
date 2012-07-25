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
        ref: 'selectorHoras'
    }],
    init : function()
    {
        this.control(
        {
            'panelHorarios filtroGrupos button[name=intervaloHorario]' :
            {
                click : this.showIntervaloHorario
            },
            'selectorIntervaloHorario button[action=cancel]' :
            {
                click : function(button)
                {
                    button.up("selectorIntervaloHorario").destroy();
                }
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
                select : function() {
                    //this.getSelectorIntervaloHorario().show();
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
        var horaInicio = this.getSelectorHoras().down('combobox[name=horaInicio]').getValue();
        var horaFin = this.getSelectorHoras().down('combobox[name=horaFin]').getValue();

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

        console.log(this.getFiltroGrupos());
        console.log(estudio, curso, semestre, grupo);
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

                    this.getSelectorHoras().down('combobox[name=horaInicio]').setValue(horaInicio);
                    this.getSelectorHoras().down('combobox[name=horaFin]').setValue(horaFin);

                }

            }
        });
    }
});
