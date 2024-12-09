using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Clientes.Data.Migrations
{
    public partial class classes2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Condicoes",
                columns: table => new
                {
                    CondicoesId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Condicoes = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Condicoes", x => x.CondicoesId);
                });

            migrationBuilder.CreateTable(
                name: "EnvioFaturas",
                columns: table => new
                {
                    FaturaId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Fatura = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EnvioFaturas", x => x.FaturaId);
                });

            migrationBuilder.CreateTable(
                name: "Estados",
                columns: table => new
                {
                    EstadoId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    EstadoNome = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Estados", x => x.EstadoId);
                });

            migrationBuilder.CreateTable(
                name: "Leads",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nome = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    PrimeiroContacto = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    AFazer = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    EstadoLead = table.Column<bool>(type: "bit", nullable: false),
                    Reuniao = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Proposta = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Resultados = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Observacoes = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Leads", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "MetodosPagamento",
                columns: table => new
                {
                    MetodoId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Metodo = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MetodosPagamento", x => x.MetodoId);
                });

            migrationBuilder.CreateTable(
                name: "Clientes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nome = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Morada = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Localidade = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CodigoPostal = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Distrito = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Representante = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Email = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Telefone = table.Column<int>(type: "int", nullable: false),
                    Website = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    NIF = table.Column<int>(type: "int", nullable: false),
                    PlanoContratado = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CPagamentoId = table.Column<int>(type: "int", nullable: false),
                    MPagamentoId = table.Column<int>(type: "int", nullable: false),
                    EnvioFaturaId = table.Column<int>(type: "int", nullable: false),
                    PagamentosOnline = table.Column<bool>(type: "bit", nullable: false),
                    VivaWallet = table.Column<bool>(type: "bit", nullable: false),
                    ContratoDPD = table.Column<bool>(type: "bit", nullable: false),
                    Tags = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Clientes", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Clientes_Condicoes_CPagamentoId",
                        column: x => x.CPagamentoId,
                        principalTable: "Condicoes",
                        principalColumn: "CondicoesId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Clientes_EnvioFaturas_EnvioFaturaId",
                        column: x => x.EnvioFaturaId,
                        principalTable: "EnvioFaturas",
                        principalColumn: "FaturaId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Clientes_MetodosPagamento_MPagamentoId",
                        column: x => x.MPagamentoId,
                        principalTable: "MetodosPagamento",
                        principalColumn: "MetodoId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Agendamentos",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Data = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Tipo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    EstadoId = table.Column<int>(type: "int", nullable: false),
                    ClienteID = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Agendamentos", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Agendamentos_Clientes_ClienteID",
                        column: x => x.ClienteID,
                        principalTable: "Clientes",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Agendamentos_Estados_EstadoId",
                        column: x => x.EstadoId,
                        principalTable: "Estados",
                        principalColumn: "EstadoId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Agendamentos_ClienteID",
                table: "Agendamentos",
                column: "ClienteID");

            migrationBuilder.CreateIndex(
                name: "IX_Agendamentos_EstadoId",
                table: "Agendamentos",
                column: "EstadoId");

            migrationBuilder.CreateIndex(
                name: "IX_Clientes_CPagamentoId",
                table: "Clientes",
                column: "CPagamentoId");

            migrationBuilder.CreateIndex(
                name: "IX_Clientes_EnvioFaturaId",
                table: "Clientes",
                column: "EnvioFaturaId");

            migrationBuilder.CreateIndex(
                name: "IX_Clientes_MPagamentoId",
                table: "Clientes",
                column: "MPagamentoId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Agendamentos");

            migrationBuilder.DropTable(
                name: "Leads");

            migrationBuilder.DropTable(
                name: "Clientes");

            migrationBuilder.DropTable(
                name: "Estados");

            migrationBuilder.DropTable(
                name: "Condicoes");

            migrationBuilder.DropTable(
                name: "EnvioFaturas");

            migrationBuilder.DropTable(
                name: "MetodosPagamento");
        }
    }
}
