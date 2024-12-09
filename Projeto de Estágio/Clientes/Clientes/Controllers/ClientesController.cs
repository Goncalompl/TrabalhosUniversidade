using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Clientes.Data;
using Clientes.Models;
using System.Runtime.InteropServices;
using Microsoft.AspNetCore.Hosting;
using IHostingEnvironment = Microsoft.AspNetCore.Hosting.IHostingEnvironment;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Hosting.Internal;
using Clientes.Data.Migrations;
using System.IO;
using Microsoft.AspNetCore.Http;

namespace Clientes.Controllers
{
    public class ClientesController : Controller
    {
        
        private readonly ApplicationDbContext _context;
        private readonly IWebHostEnvironment _webHostEnvironment;
        private readonly IHostingEnvironment _hostingEnv;

        

        public ClientesController(ApplicationDbContext context, IWebHostEnvironment webHostEnvironment, IHostingEnvironment hostingEnv)
        {
            _context = context;
            _webHostEnvironment = webHostEnvironment;
            _hostingEnv = hostingEnv;
        }

        // GET: Clientes
        public async Task<IActionResult> Index(string TextoAPesquisar, string TextoAPesquisarTag, int Nome, int Servico, int Localizacao, int Morada,int Tags,int Lista)
        {





            if (TextoAPesquisar != null)
            {

                return View(await _context.Clientes.Where(c => c.Nome.Contains(TextoAPesquisar)|| c.NomeComercial.Contains(TextoAPesquisar) || c.Localidade.Contains(TextoAPesquisar)).OrderBy(c => c.Nome).ToListAsync());
            }
            

            if (TextoAPesquisarTag != null)
            {
                return View(await _context.Clientes.Where(c => c.Tags.Contains(TextoAPesquisarTag)).OrderBy(c => c.Tags).ToListAsync());
            }

            var applicationDbContext = _context.Agendamentos.Include(a => a.Cliente);


            if (Nome == 1)
            {
                return this.View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
            }
           

            if (Nome == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (Servico == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Representante).ToListAsync());
            }

            if (Servico == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Representante).ToListAsync());
            }

            if (Localizacao == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Localidade).ToListAsync());
            }

            if (Localizacao == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Localidade).ToListAsync());
            }

            if (Morada == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Morada).ToListAsync());
            }

            if (Morada == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Morada).ToListAsync());
            }
            if (Tags == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Tags).ToListAsync());
            }

            if (Tags == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Tags).ToListAsync());
            }

            if (Lista == 1)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == false).OrderBy(c => c.Nome).ToListAsync());
            }


            if (Lista == 2)
            {

                return this.View(await _context.Clientes.Where(c => c.ALista == false && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            if (Lista == 3)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }


            return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
        }

        // GET: Clientes/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Clientes == null)
            {
                return NotFound();
            }

            var cliente = await _context.Clientes
                .Include(c => c.CPagamento)
                .Include(c => c.EnvioFatura)
                .Include(c => c.MPagamento)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (cliente == null)
            {
                return NotFound();
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes");
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura");
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo");

            return View(cliente);
        }

        // GET: Clientes/Create
        public IActionResult Create()
        {
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes");
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura");
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo");
            return View();
        }

        // POST: Clientes/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(string? nome, string? email, int telefone, [Bind("Id,Nome,NomeComercial,Morada,Localidade,CodigoPostal,Distrito,Representante,Email,Telefone,Telemovel,Website,NIF,PlanoContratado,ALista,AListaDigital,Rs,Site,Fotografia,CPagamentoId,MPagamentoId,EnvioFaturaId,PagamentosOnline,VivaWallet,ContratoDPD,Tags,DataFaturacao,UltimaVisita,Necessidade,DataAdesao,DataFim,Custo,PdfFile,Observacoes")] Cliente cliente)
        {
            ModelState.Remove(nameof(cliente.CPagamento));
            ModelState.Remove(nameof(cliente.MPagamento));
            ModelState.Remove(nameof(cliente.EnvioFatura));


            var cliente1 = new Cliente();
            cliente1.Nome = nome;
            cliente1.Email = email;
            cliente1.Telefone = telefone;

            if (cliente.PdfFile != null)
            {
 

                if(cliente.Pdf != null)
                {


                    using var dataStream = new MemoryStream();



                    await cliente.PdfFile.CopyToAsync(dataStream);
                    cliente.Pdf = dataStream.ToArray();

                    string rootPath = _hostingEnv.WebRootPath.Replace(@"\\", "/");
                    string dirPath = String.Format("{0}/pdf", rootPath);
                    string fileName = "random.pdf";
                    string filePath = String.Format("{0}/pdf/{1}", rootPath, fileName);
                    using (MemoryStream ms = new MemoryStream(cliente.Pdf))
                    {
                        bool directoryExists = Directory.Exists(dirPath);
                        if (directoryExists != true)
                        {
                            Directory.CreateDirectory(dirPath);
                        }

                        using var stream = System.IO.File.Create(filePath);
                        await cliente.PdfFile.CopyToAsync(stream);


                    }
                }

                
                

                if (nome == null)
                {
                    if (ModelState.IsValid)
                    {


                        _context.Add(cliente1);
                        await _context.SaveChangesAsync();
                        return RedirectToAction(nameof(Index));
                    }
                    
                }

            }


            if (ModelState.IsValid)
                {


                

                    _context.Add(cliente);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente1.CPagamentoId);
                ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente1.EnvioFaturaId);
                ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente1.MPagamentoId);
                return View(cliente);
            
        }

        // GET: Clientes/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Clientes == null)
            {
                return NotFound();
            }

            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente == null)
            {
                return NotFound();
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }

        // POST: Clientes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,Nome,NomeComercial,Morada,Localidade,CodigoPostal,Distrito,Representante,Email,Telefone,Telemovel,Website,NIF,PlanoContratado,ALista,AListaDigital,Rs,Site,Fotografia,CPagamentoId,MPagamentoId,EnvioFaturaId,PagamentosOnline,VivaWallet,ContratoDPD,Tags,DataFaturacao,UltimaVisita,Necessidade,DataAdesao,DataFim,Custo,CoverPdf,Observacoes ")] Cliente cliente)
        {
            if (id != cliente.Id)
            {
                return NotFound();
            }
            ModelState.Remove(nameof(cliente.CPagamento));
            ModelState.Remove(nameof(cliente.MPagamento));
            ModelState.Remove(nameof(cliente.EnvioFatura));

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(cliente);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ClienteExists(cliente.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }

        // GET: Clientes/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes");
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura");
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo");
            if (id == null || _context.Clientes == null)
            {
                return NotFound();
            }

            var cliente = await _context.Clientes
                .Include(c => c.CPagamento)
                .Include(c => c.EnvioFatura)
                .Include(c => c.MPagamento)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (cliente == null)
            {
                return NotFound();
            }

            return View(cliente);
        }

        // POST: Clientes/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes");
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura");
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo");
            if (_context.Clientes == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Clientes'  is null.");
            }
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente != null)
            {
                _context.Clientes.Remove(cliente);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ClienteExists(int id)
        {
          return _context.Clientes.Any(e => e.Id == id);
        }

        public async Task<IActionResult> Contactos(string TextoAPesquisar, int nome, int email, int telefone,int telemovel,int website,int Lista, int nr = 0)
        {
            if (TextoAPesquisar != null && nr == 0)
            {
                return View(await _context.Clientes.Where(c => c.Email.Contains(TextoAPesquisar)|| c.NomeComercial.Contains(TextoAPesquisar) || c.Nome.Contains(TextoAPesquisar) || c.Website.Contains(TextoAPesquisar)).ToListAsync());
            }
            else if (TextoAPesquisar == null && nr != 0)
            {
                return View(await _context.Clientes.Where(c => c.Telefone.Equals(nr) || c.Telemovel.Equals(nr)).ToListAsync());
            }

            if (nome == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
            }

            if (nome == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (email == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Email).ToListAsync());
            }

            if (email == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Email).ToListAsync());
            }

            if (telefone == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Telefone).ToListAsync());
            }

            if (telefone == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Telefone).ToListAsync());
            }

            if (telemovel == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Telemovel).ToListAsync());
            }

            if (telemovel == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Telemovel).ToListAsync());
            }
            if (website == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Website).ToListAsync());
            }

            if (website == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Website).ToListAsync());
            }
            if (Lista == 1)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == false).OrderBy(c => c.Nome).ToListAsync());
            }


            if (Lista == 2)
            {

                return this.View(await _context.Clientes.Where(c => c.ALista == false && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            if (Lista == 3)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }


            return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
        }







        // GET: Clientes/Edit/5
        public async Task<IActionResult> CriarClienteLeads(string? nome, string email, int telefone)
        {


            var cliente = new Cliente();
            cliente.Nome = nome;
            cliente.Email = email;
            cliente.Telefone = telefone;
            if (cliente == null)
            {
                return NotFound();
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }

        // POST: Clientes/CriarCleinteLeads/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> CriarClienteLeads(int id, [Bind("Id,Nome,NomeComercial,Morada,Localidade,CodigoPostal,Distrito,Representante,Email,Telefone,Telemovel,Website,NIF,PlanoContratado,ALista,AListaDigital,Rs,Site,Fotografia,CPagamentoId,MPagamentoId,EnvioFaturaId,PagamentosOnline,VivaWallet,ContratoDPD,Tags,DataFaturacao,UltimaVisita,Necessidade,DataAdesao,DataFim,Custo,CoverPdf,Observacoes ")] Cliente cliente)
        {
            if (id != cliente.Id)
            {
                return NotFound();
            }
            ModelState.Remove(nameof(cliente.CPagamento));
            ModelState.Remove(nameof(cliente.MPagamento));
            ModelState.Remove(nameof(cliente.EnvioFatura));

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(cliente);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ClienteExists(cliente.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }


        public async Task<IActionResult> EditarContactos(int? id, string Nome)
        {
            if (id == null || _context.Clientes == null)
            {
                return NotFound();
            }
          


            var cliente = await _context.Clientes.FindAsync(id);
            

            if (cliente == null)
            {
                
                return NotFound();
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }

        // POST: Clientes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> EditarContactos(int id,string Nome, [Bind("Telefone,Telemovel,Website,Email ")] Cliente cliente)
        {
            if (id != cliente.Id)
            {
                return NotFound();
            }
            ModelState.Remove(nameof(cliente.CPagamento));
            ModelState.Remove(nameof(cliente.MPagamento));
            ModelState.Remove(nameof(cliente.EnvioFatura));
            var temp = await _context.Clientes.FindAsync(id);
            
        
            if (ModelState.IsValid)
            {
             

                try
                {
                    cliente.Nome = Nome;
                    _context.Update(cliente);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ClienteExists(cliente.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes", cliente.CPagamentoId);
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura", cliente.EnvioFaturaId);
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo", cliente.MPagamentoId);
            return View(cliente);
        }

        public async Task<IActionResult> Custos(int Nome, int Adesao, int Custo, int Fim, string TextoAPesquisar, DateTime data1, DateTime data2, int Lista,int nr = 0)
        {
            if (TextoAPesquisar != null && nr == 0)
            {
                return View(await _context.Clientes.Where(c => c.Nome.Contains(TextoAPesquisar)).ToListAsync());
            }
            else if (TextoAPesquisar == null && nr != 0)
            {
                return View(await _context.Clientes.Where(c => c.Custo.Equals(nr)).ToListAsync());
            }

            if (Nome == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
            }

            if (Nome == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Nome).ToListAsync());
            }

            if (Adesao == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.DataAdesao).ToListAsync());
            }

            if (Adesao == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.DataAdesao).ToListAsync());
            }

            if (Custo == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.Custo).ToListAsync());
            }

            if (Custo == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.Custo).ToListAsync());
            }

            if (Fim == 1)
            {
                return View(await _context.Clientes.OrderBy(c => c.DataFim).ToListAsync());
            }

            if (Fim == 2)
            {

                return View(await _context.Clientes.OrderByDescending(c => c.DataFim).ToListAsync());
            }
            if (Lista == 1)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == false).OrderBy(c => c.Nome).ToListAsync());
            }


            if (Lista == 2)
            {

                return this.View(await _context.Clientes.Where(c => c.ALista == false && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            if (Lista == 3)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
            return View(await _context.Clientes.Where(c => c.DataAdesao >= data1 && c.DataAdesao <= data2 || c.DataFim >= data1 && c.DataFim <= data2 /*&& c.Estado.EstadoNome != "Realizado" || c.Estado.EstadoNome == "Remarcar"*/).ToListAsync());

        }


        public async Task<IActionResult> ListaClientes(string TextoAPesquisar, string TextoAPesquisarTag, int Nome, int Servico, int Localizacao, int Morada, int Tags, int Lista, string nome)
        {
            if (TextoAPesquisar != null)
            {

                return View(await _context.Clientes.Where(c => c.Nome.Contains(TextoAPesquisar) || c.Localidade.Contains(TextoAPesquisar)).OrderBy(c => c.Nome).ToListAsync());
            }



            if (TextoAPesquisarTag != null)
            {
                return View(await _context.Clientes.Where(c => c.Tags.Contains(TextoAPesquisarTag)).OrderBy(c => c.Tags).ToListAsync());
            }

            if(Lista == 1)
            {
                return View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == false).OrderBy(c => c.Nome).ToListAsync());
            }
            if (Lista == 2)
            {
                return View(await _context.Clientes.Where(c => c.AListaDigital == true && c.ALista == false).OrderBy(c => c.Nome).ToListAsync());
            }
            if (Lista == 3)
            {
                return View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital).OrderBy(c => c.Nome).ToListAsync());
            }

            var applicationDbContext = _context.Agendamentos.Include(a => a.Cliente);





            return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
        }

        public async Task<IActionResult> ListaContactos(string TextoAPesquisar,int Lista,int nr = 0)
        {
            if (TextoAPesquisar != null && nr == 0)
            {
                return View(await _context.Clientes.Where(c => c.Email.Contains(TextoAPesquisar) || c.Nome.Contains(TextoAPesquisar) || c.Website.Contains(TextoAPesquisar)).ToListAsync());
            }
            else if (TextoAPesquisar == null && nr != 0)
            {
                return View(await _context.Clientes.Where(c => c.Telefone.Equals(nr)|| c.Telemovel.Equals(nr)).ToListAsync());
            }

            if (Lista == 1)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == false).OrderBy(c => c.Nome).ToListAsync());
            }


            if (Lista == 2)
            {

                return this.View(await _context.Clientes.Where(c => c.ALista == false && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            if (Lista == 3)
            {
                return this.View(await _context.Clientes.Where(c => c.ALista == true && c.AListaDigital == true).OrderBy(c => c.Nome).ToListAsync());
            }

            return View(await _context.Clientes.OrderBy(c => c.Nome).ToListAsync());
        }
       
        public string Result { get; private set; }
        //private async Task<string> UploadPdf( IFormFile file)
        //{
        //    if (file.Length == 0)
        //    {
        //        Result = "Ficheiro vazio!";
        //        return "Vazio";
        //    }

        //    var filePath = Path.GetTempFileName();
        //    using (var stream = new FileStream(filePath, FileMode.Create))
        //    {
        //        await file.CopyToAsync(stream);
        //    }
        //    return filePath;
        //}

        public async Task<IActionResult> ContractoDetalhes(int? id)
        {
            if (id == null || _context.Clientes == null)
            {
                return NotFound();
            }

            var cliente = await _context.Clientes
                .Include(c => c.CPagamento)
                .Include(c => c.EnvioFatura)
                .Include(c => c.MPagamento)
                .FirstOrDefaultAsync(m => m.Id == id);
            if (cliente == null)
            {
                return NotFound();
            }
            ViewData["CPagamentoId"] = new SelectList(_context.Condicoes, "CondicoesId", "Condicoes");
            ViewData["EnvioFaturaId"] = new SelectList(_context.EnvioFaturas, "FaturaId", "Fatura");
            ViewData["MPagamentoId"] = new SelectList(_context.MetodosPagamento, "MetodoId", "Metodo");

            return View(cliente);
        }



    }
}
