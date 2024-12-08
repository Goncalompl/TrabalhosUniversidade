
using Microsoft.AspNetCore.Components.Forms;
using System.ComponentModel.DataAnnotations;

using System.ComponentModel.DataAnnotations;

using System.Xml.Linq;
using TrabalhoFinalRentCar.Models;

namespace TrabalhoFinalRentCar.ViewModels
{
    public class PesquisaVeiculoViewModel
    {
        public List<Veiculo> ListaDeVeiculos { get; set; }
        public int NumResultados { get; set; }

        [Display(Name = "PESQUISA DE Veiculos ...", Prompt = "introduza o texto a pesquisar")]
        public string TextoAPesquisar { get; set; }
        public DateTime DataLevantamento { get; set; }

        public DateTime DataEntrega { get; set; }




    }



}
