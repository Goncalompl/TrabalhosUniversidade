using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using TrabalhoFinalRentCar.Data;
using TrabalhoFinalRentCar.Models;

namespace TrabalhoFinalRentCar.Controllers
{
    namespace RentACar.Controllers
    {
        public class VeiculosController : Controller
        {
            private readonly ApplicationDbContext _context;

            public VeiculosController(ApplicationDbContext context)
            {
                _context = context;
            }

            // GET: Veiculos
            public async Task<IActionResult> Index(int? preco)
            {
               ViewData["ListaDeVeiculos"] = new SelectList(_context.Veiculo.ToList());
                ViewData["ListaDeEmpresas"] = new SelectList(_context.Empresas.ToList());
                ViewData["ListaDeReview"] = new SelectList(_context.Reviews.ToList());
                ViewData["EmpresaId"] = new SelectList(_context.Empresas, "Id", "Nome");
             
                if (preco == 1)
                {
                    return View(await _context.Veiculo.OrderBy(c => c.Custo).ToListAsync());
                }

                if (preco == 2)
                {

                    return View(await _context.Veiculo.OrderByDescending(c => c.Custo).ToListAsync());
                }

                if (preco == 3)
                {

                    return View(await _context.Veiculo.OrderBy(c => c.EmpresaAssociada.Review).ToListAsync());
                }


                return View(await _context.Veiculo.ToListAsync());
            }



            [HttpPost]
            public async Task<IActionResult> Index(String TextoAPesquisar, DateTime DataLevantamento, DateTime DataEntrega)
            {
                ViewData["Title"] = "Lista de Veiculos com '" + DataLevantamento + DataEntrega + "'";
                ViewData["ListaDeVeiculos"] = new SelectList(_context.Veiculo.ToList());
                ViewData["ListaDeEmpresas"] = new SelectList(_context.Empresas.ToList());
                ViewData["ListaDeReview"] = new SelectList(_context.Reviews.ToList());
                ViewData["EmpresaId"] = new SelectList(_context.Empresas, "Id", "Nome");


                if (TextoAPesquisar != null)
                {
                    return View(await _context.Veiculo.Where(c => c.Localizacao.Equals(TextoAPesquisar) || c.Tipo.Equals(TextoAPesquisar)).ToListAsync());
                }


                if (DataLevantamento != null)
                {
                    return View(await _context.Veiculo.Where(c => c.DataLevantamento.Equals(DataLevantamento)).ToListAsync());
                }

                if (DataEntrega != null)
                {
                    return View(await _context.Veiculo.Where(c => c.DataEntrega.Equals(DataEntrega)).ToListAsync());
                }



                return null;

            }


            public async Task<IActionResult> IndexFuncionario(int? preco)
            {
                ViewData["ListaDeVeiculos"] = new SelectList(_context.Veiculo.ToList());
                ViewData["ListaDeEmpresas"] = new SelectList(_context.Empresas.ToList());
                ViewData["ListaDeReview"] = new SelectList(_context.Reviews.ToList());
                ViewData["EmpresaId"] = new SelectList(_context.Empresas, "Id", "Nome");

                if (preco == 1)
                {
                    return View(await _context.Veiculo.OrderBy(c => c.Custo).ToListAsync());
                }

                if (preco == 2)
                {

                    return View(await _context.Veiculo.OrderByDescending(c => c.Custo).ToListAsync());
                }

                if (preco == 3)
                {

                    return View(await _context.Veiculo.OrderBy(c => c.EmpresaAssociada.Review).ToListAsync());
                }


                return View(await _context.Veiculo.ToListAsync());
            }




            // GET: Veiculos/Details/5
            public async Task<IActionResult> Details(int? id)
            {
                if (id == null || _context.Veiculo == null)
                {
                    return NotFound();
                }

                var veiculo = await _context.Veiculo
                    .FirstOrDefaultAsync(m => m.Id == id);
                if (veiculo == null)
                {
                    return NotFound();
                }

                return View(veiculo);
            }

            // GET: Veiculos/Create
            public IActionResult Create()
            {
                return View();
            }

            // POST: Veiculos/Create
            // To protect from overposting attacks, enable the specific properties you want to bind to.
            // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
            [HttpPost]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> Create([Bind("Id,Marca,Matricula,Tipo,Localizacao,DataLevantamento,DataEntrega,Custo")] Veiculo veiculo)
            {
                if (ModelState.IsValid)
                {
                    _context.Add(veiculo);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                return View(veiculo);
            }

            // GET: Veiculos/Edit/5
            public async Task<IActionResult> Edit(int? id)
            {
                if (id == null || _context.Veiculo == null)
                {
                    return NotFound();
                }

                var veiculo = await _context.Veiculo.FindAsync(id);
                if (veiculo == null)
                {
                    return NotFound();
                }
                return View(veiculo);
            }

            // POST: Veiculos/Edit/5
            // To protect from overposting attacks, enable the specific properties you want to bind to.
            // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
            [HttpPost]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> Edit(int id, [Bind("Id,Marca,Matricula,Tipo,Localizacao,DataLevantamento,DataEntrega,Custo")] Veiculo veiculo)
            {
                if (id != veiculo.Id)
                {
                    return NotFound();
                }

                if (ModelState.IsValid)
                {
                    try
                    {
                        _context.Update(veiculo);
                        await _context.SaveChangesAsync();
                    }
                    catch (DbUpdateConcurrencyException)
                    {
                        if (!VeiculoExists(veiculo.Id))
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
                return View(veiculo);
            }

            // GET: Veiculos/Delete/5
            public async Task<IActionResult> Delete(int? id)
            {
                if (id == null || _context.Veiculo == null)
                {
                    return NotFound();
                }

                var veiculo = await _context.Veiculo
                    .FirstOrDefaultAsync(m => m.Id == id);
                if (veiculo == null)
                {
                    return NotFound();
                }

                return View(veiculo);
            }

            // POST: Veiculos/Delete/5
            [HttpPost, ActionName("Delete")]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> DeleteConfirmed(int id)
            {
                if (_context.Veiculo == null)
                {
                    return Problem("Entity set 'ApplicationDbContext.Veiculo'  is null.");
                }
                var veiculo = await _context.Veiculo.FindAsync(id);
                if (veiculo != null)
                {
                    _context.Veiculo.Remove(veiculo);
                }

                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }

            private bool VeiculoExists(int id)
            {
                return _context.Veiculo.Any(e => e.Id == id);
            }
        }
    }

}
