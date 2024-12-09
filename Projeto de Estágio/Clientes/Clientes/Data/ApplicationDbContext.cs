using Clientes.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace Clientes.Data
{
    public class ApplicationDbContext : IdentityDbContext
    {
       
        public DbSet<Cliente> Clientes { get; set; }
        public DbSet<Agendamento> Agendamentos { get; set; }
        public DbSet<CondicoesPagamento> Condicoes { get; set; }
        public DbSet<MetodoPagamento> MetodosPagamento { get; set; }
        public DbSet<EnvioFatura> EnvioFaturas { get; set; }
        public DbSet<Estado> Estados { get; set; }
        public DbSet<Leads> Leads { get; set; }
        public DbSet<Campanha> Campanhas { get; set; }
        public DbSet<Nota> Notas { get; set; }

        public DbSet<PdfFiles> PdfFiles { get; set; }



        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        
       
        
    }
}