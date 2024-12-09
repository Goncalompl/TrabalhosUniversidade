using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Xml.Linq;

namespace Clientes.Models
{
    public class Cliente
    {
        public int Id { get; set; }
        [Display(Name = "Nome", Prompt = "Introduza o Nome do Cliente", Description = "Nome do Cliente")]
        public string Nome { get; set; }
        [Display(Name = "Nome Empresa", Prompt = "Introduza o Nome da Empresa", Description = "Nome Comercial")]
        public string? NomeComercial { get; set; }
        [Display(Name = "Morada", Prompt = "Cidade")]
        public string? Morada { get; set; }
        public string? Localidade { get; set; }
        [Display(Name = "Código Postal")]
        public string? CodigoPostal { get; set; }
        public string? Distrito { get; set; }
        [Display(Name = "Representante da Empresa")]
        public string Representante { get; set; }
        public string? Email { get; set; }
        [PersonalData]
        [Range(100000000, 999999999, ErrorMessage = "NUMERO DE TELEFONE INVALIDO")]
        public int? Telefone { get; set; }
        [PersonalData]
        [Range(100000000, 999999999, ErrorMessage = "NUMERO DE TELEMOVEL INVALIDO")]
        [Display(Name = "Telemóvel")]
        public int? Telemovel { get; set; }
        public string? Website { get; set; }
        [PersonalData]
        [Range(100000000, 999999999, ErrorMessage = "NIF OU NIPC Inválido")]
        [Display(Name = "NIF/NIPC")]
        public int? NIF { get; set; }
        [Display(Name = "ALista")]
        public bool ALista { get; set; }
        [Display(Name = "AListaDigital")]
        public bool AListaDigital { get; set; }
        [Display(Name = "Rs")]
        public bool Rs { get; set; }
        [Display(Name = "Site")]
        public bool Site { get; set; }
        [Display(Name = "Fotografia")]
        public bool Fotografia { get; set; }
        [Display(Name = "Condições de Pagamento")]
        public int CPagamentoId { get; set; }
        [Display(Name = "Condições de Pagamento")]
        public CondicoesPagamento? CPagamento { get; set; }
        [Display(Name = "Método de Pagamento")]
        public int MPagamentoId { get; set; }
        [Display(Name = "Método de Pagamento")]
        public MetodoPagamento? MPagamento { get; set; }
        [Display(Name = "Método de Envio da Fatura")]
        public int EnvioFaturaId { get; set; }
        [Display(Name = "Método de Envio da Fatura")]
        public EnvioFatura? EnvioFatura { get; set; }
        [Display(Name = "Pagamentos Online")]
        public bool PagamentosOnline { get; set; }
        [Display(Name = "Contrato Viva Wallet")]
        public bool VivaWallet { get; set; }
        [Display(Name = "Contrato DPD")]
        public bool ContratoDPD { get; set; }
        public string? Tags  { get; set; }
        [Display(Name = "Data de Faturação")]
        [DataType(DataType.Date)]
        public DateTime? DataFaturacao { get; set; }
        [Display(Name = "Data Última Visita")]
        [DataType(DataType.Date)]
        public DateTime? UltimaVisita { get; set; }
        
        [Display(Name = "Oportunidade")]
        public string? Necessidade { get; set; }
        [Display(Name = "Data de Adesão a Lista")]
        [DataType(DataType.Date)]
        public DateTime? DataAdesao { get; set; }

        [Display(Name = "Data de Fim")]
        [DataType(DataType.Date)]
        public DateTime? DataFim { get; set; }
        [Display(Name = "Valor do Contrato")]
        public int? Custo { get; set; }
        [Display(Name = "Observações")]
        public string? Observacoes { get; set; }

        public byte[]? Pdf { get; set; }
        [NotMapped]
        public IFormFile? PdfFile { get; set; }
    }
}