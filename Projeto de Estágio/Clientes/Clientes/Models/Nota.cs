using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;

namespace Clientes.Models
{
    public class Nota
    {
        public int Id { get; set; }
        [Display(Name = "Título", Prompt = "Título*")]
        public string Titulo { get; set; }
        [Display(Name = "Nota", Prompt = "Nota*")]
        public string? TextoNota { get; set; }
        [Display(Name = "Data a Realizar", Prompt = "Data a Realizar")]
        [DataType(DataType.Date)]
        public DateTime? DataRealizar { get; set; }
        [Display(Name = "Responsável", Prompt = "Responsável")]
        public string Responsavel { get; set; }
    }
}
