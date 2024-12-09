using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Drawing;
using System.Xml.Linq;

namespace Clientes.Models
{
    public class Campanha
    {
        public int CampanhaId { get; set; }

        [Display(Name = "Plataforma", Prompt = "Introduza a Plataforma da Campanha*")]
        public string Plataforma { get; set; }
        [DataType(DataType.Date)]
        public DateTime? Data { get; set; }
        [Display(Name = "Duração")]
        [NotMapped]
        public TimeOnly? Duracao { get; set; }
        [Display(Name = "Investimento monetário", Prompt = "Investimento*")]
        public int? Investimento { get; set; }
        [Display(Name = "Interações", Prompt = "Número de Interações*")]
        public string? Interacao { get; set; }
        [Display(Name = "Descrição", Prompt = "Descrição*")]
        public string? Descricao { get; set; }
        [Display(Name = "Leads", Prompt = "Leads*")]
        public string? Leads { get; set; }
        [Display(Name = "Online", Prompt = "Online")]
        public bool Online { get; set; } = true;
        [Display(Name = "Cliente", Prompt = "Cliente")]
        public string? ClienteCampanha { get; set; }
        [Display(Name = "Responsável", Prompt = "Responsável")]
        public string Responsavel { get; set; }

    }
}
