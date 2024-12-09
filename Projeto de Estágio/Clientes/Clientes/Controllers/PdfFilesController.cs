using Clientes.Data.Migrations;
using Microsoft.AspNetCore.Mvc;
using Clientes.Models;
using System.Configuration;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Clientes.Data;
using PdfFiles = Clientes.Models.PdfFiles;
using Microsoft.Extensions.Hosting.Internal;


namespace Clientes.Controllers
{
    public class PdfFilesController : Controller

    {
        [Obsolete]
        IWebHostEnvironment _hostingEnvironment = null;
        private  ApplicationDbContext _context;

        public PdfFilesController(Microsoft.AspNetCore.Hosting.IWebHostEnvironment hostingEnvironment, ApplicationDbContext context)
        {
            _hostingEnvironment = hostingEnvironment;
            _context = context;
        }

        [HttpGet]
        public IActionResult Index(string fileName = "")
        {
            ViewData["ClienteID"] = new SelectList(_context.Clientes, "Id", "Nome");
            PdfFiles fileObj = new PdfFiles();
            fileObj.Name = fileName;

           
         


            return View(fileObj);
        }

        [HttpPost]
        public IActionResult Index([FromServices] IWebHostEnvironment hostingEnvironment, IFormCollection fileObj)
        {
            ViewData["ClienteID"] = new SelectList(_context.Clientes, "Id", "Nome");
            ModelState.Remove(nameof(PdfFiles.ClientePdf));
            

            
            string value = fileObj["ClientePdfID"].ToString();
            
            List<PdfFiles> selectedFiles = new List<PdfFiles>();

            foreach (var f in fileObj.Files)
            {
                string currentFileName = f.FileName;
                string fileName = "Contracto_" + DateTime.Now.ToString("dd_MM_yyyy_hh_mm");
                string filePath = Path.Combine(hostingEnvironment.WebRootPath, "pdf", fileName);

                using (FileStream fileStream = System.IO.File.Create(filePath))
                {
                    f.CopyTo(fileStream);
                    fileStream.Flush();
                }

                var newFile = new PdfFiles()
                {
                   
                    Name = currentFileName,
                    Path = fileName ,
                    ClientePdfID = int.Parse(value),
                };

                selectedFiles.Add(newFile);
                _context.PdfFiles.Add(newFile);
                _context.SaveChanges();
            }


            return Index();
        }

        public IActionResult PDFViewerNewTab(string fileName)
        {
            string path = _hostingEnvironment.WebRootPath + "\\pdf\\" + fileName;
            return File(System.IO.File.ReadAllBytes(path), "application/pdf");
        }




        public async Task<IActionResult> Details(int cliente, int nome, string TextoAPesquisar, string nomecomercial)

        {

            var applicationDbContext = _context.PdfFiles.Include(a => a.ClientePdf);


            ViewData["ClienteID"] = new SelectList(_context.Clientes, "Id", "Nome");

            if (TextoAPesquisar != null)
            {

                return View(await _context.PdfFiles.Where(c => c.ClientePdf.Nome.Contains(TextoAPesquisar)).OrderBy(c => c.Name).Include(c => c.ClientePdf).ToListAsync());
            }


            if (nomecomercial != null)
            {
                return this.View(await _context.PdfFiles.Where(c => c.ClientePdf.Nome.Equals(nomecomercial)).Include(a => a.ClientePdf).ToListAsync());
            }

            if (cliente == 1)
            {
                return this.View(await _context.PdfFiles.OrderBy(c => c.ClientePdf.Nome).Include(a => a.ClientePdf).ToListAsync());
            }


            if (cliente == 2)
            {

                return View(await _context.PdfFiles.OrderByDescending(c => c.ClientePdf.Nome).Include(a => a.ClientePdf).ToListAsync());
            }

            if (nome == 1)
            {
                return this.View(await _context.PdfFiles.OrderBy(c => c.Name).Include(a => a.ClientePdf).ToListAsync());
            }


            if (nome == 2)
            {

                return View(await _context.PdfFiles.OrderByDescending(c => c.Name).Include(a => a.ClientePdf).ToListAsync());
            }

            return View(await applicationDbContext.ToListAsync());
        }

        // GET: Notas/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.PdfFiles == null)
            {
                return NotFound();
            }

            var nota = await _context.PdfFiles
                .FirstOrDefaultAsync(m => m.FileId == id);
            if (nota == null)
            {
                return NotFound();
            }

            return View(nota);
        }

        // POST: Notas/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.PdfFiles == null)
            {
                return Problem("Entity set 'ApplicationDbContext.Notas'  is null.");
            }
            var nota = await _context.PdfFiles.FindAsync(id);
            if (nota != null)
            {
                _context.PdfFiles.Remove(nota);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Details));
        }
        private bool PdfFileExists(int id)
        {
            return _context.PdfFiles.Any(e => e.FileId == id);
        }

        // GET: Clientes/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.PdfFiles == null)
            {
                return NotFound();
            }

            var pdfFiles = await _context.PdfFiles.FindAsync(id);
            if (pdfFiles == null)
            {
                return NotFound();
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes, "Id", "Nome");
            return View(pdfFiles);
        }

        // POST: Clientes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("FileId,Name,Path,ClientePdfID")] PdfFiles pdfFiles)
        {
            if (id != pdfFiles.FileId)
            {
                return NotFound();
            }
            ModelState.Remove(nameof(pdfFiles.ClientePdf));
            

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(pdfFiles);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!PdfFileExists(pdfFiles.FileId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Details));
            }
            ViewData["ClienteID"] = new SelectList(_context.Clientes, "Id", "Nome");
            return View(pdfFiles);
        }

    }
}