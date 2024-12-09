using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;


namespace Clientes.Models
{
    public class Agendamento
    {
        [Key]
        public int Id { get; set; }
        public DateTime Data { get; set; }
        [Display(Name = "Tipo de Reunião")]
        public string Tipo { get; set; }
        [Display(Name = "Estado")]
        public int EstadoId { get; set; }
        public Estado Estado { get; set; }
        [Display(Name = "Cliente")]
        public int? ClienteID { get; set; }
        public Cliente? Cliente { get; set; }

        [Display(Name = "Lead")]
        public int? LeadID { get; set; }
        public Leads? Lead { get; set; }

        [Display(Name = "Agendado por")]
        public string? Responsavel { get; set; }
        public string? Detalhes { get; set; }
       
    }
}
