using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;
using System.Xml.Linq;

namespace TrabalhoFinalRentCar.ViewModels
{
    public class ReservaViewModel
    {
       
        [Display(Name = "Data de Início", Prompt = "yyyy-mm-dd")]
        public DateTime DataInicio { get; set; }
        [Display(Name = "Data de Fim", Prompt = "yyyy-mm-dd")]
        public DateTime DataFim { get; set; }

        [Display(Name = "Tipo de veiculo", Prompt = "Escolha o tipo de veiculo que pretende")]
        public int TipoDeVeiculo { get; set; }
    }
}
