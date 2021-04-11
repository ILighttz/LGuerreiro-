package net.ilighttz.plugins;

import io.puharesource.mc.titlemanager.shaded.kotlinx.coroutines.BuildersKt;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class GuerreiroCommands implements CommandExecutor {

    private Plugin plugin = Main.getPlugin(Main.class);


    public static boolean iniciou = false;
    public static boolean cancelado = false;
    public static boolean aberto = false;


    ArrayList<String> onevent = new ArrayList<>();

    int avisos = (60/plugin.getConfig().getInt("avisos_por_minuto")) * plugin.getConfig ().getInt("tempo_de_entrada");
    int j = 0;
    int tempo = plugin.getConfig().getInt("tempo_de_entrada");
    int premio = plugin.getConfig ().getInt("premio");
    String ultimo = plugin.getConfig ().getString ("ultimo_vencedor");


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

        Player p = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cErro.");
        }

        if(cmd.getName().equalsIgnoreCase("guerreiro")){
            if (args.length < 1){
                p.sendMessage("");
                p.sendMessage("    §fEvento Guerreiro");
                p.sendMessage("");
                p.sendMessage(" §e/guerreiro entrar - Entre no evento guerreiro");
                p.sendMessage(" §e/guerreiro sair - Saia do evento guerreiro");
                p.sendMessage(" §e/guerreiro info - Veja informações do evento guerreiro");
                p.sendMessage("");
                if (p.hasPermission ("lguerreiro.admin")){
                    p.sendMessage("    §fFunções para Administradores");
                    p.sendMessage("");
                    p.sendMessage(" §e/guerreiro iniciar - Inicie o evento guerreiro");
                    p.sendMessage(" §e/guerreiro cancelar - Cancele o evento guerreiro");
                    p.sendMessage(" §e/guerreiro setentrada - Defina a entrada do evento guerreiro");
                    p.sendMessage(" §e/guerreiro setsaida - Defina a saida do evento guerreiro");
                    p.sendMessage(" §e/guerreiro reload - Reinicie as configurações do plugin");
                    p.sendMessage("");
                    return true;
                }
            }
            if (args.length == 1){
                String e = args[0];

                //inicio do /guerreiro info
                if (e.equalsIgnoreCase("info")){

                    Inventory inv = Bukkit.createInventory(null, 27, "§7Evento Guerreiro");

                    if (iniciou) {

                        ItemStack skull = new ItemStack (Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal ());

                        SkullMeta metaskull = (SkullMeta) skull.getItemMeta ();
                        metaskull.setOwner (plugin.getConfig ().getString ("ultimo_vencedor"));
                        metaskull.setDisplayName ("§eInformações do Evento");
                        metaskull.setLore (Arrays.asList ("", "§fStatus: §eEstá ocorrendo", "", "§fJogadores restante: §e" + j, "", "§fPremiação: §e" + plugin.getConfig().getInt("premio"), "", "§fUltimo Vencedor: §e" + plugin.getConfig().getString("ultimo_vencedor"), ""));
                        skull.setItemMeta (metaskull);
                        inv.setItem(13, skull);

                        p.openInventory(inv);
                        return true;
                    }

                    if (aberto) {

                        ItemStack skull = new ItemStack (Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal ());

                        SkullMeta metaskull = (SkullMeta) skull.getItemMeta ();
                        metaskull.setOwner (plugin.getConfig ().getString ("ultimo_vencedor"));
                        metaskull.setDisplayName ("§eInformações do Evento");
                        metaskull.setLore (Arrays.asList ("", "§fStatus: §eAberto para entrada","", "§fJogadores no evento: §e" + j, "", "§fPremiação: §e" + plugin.getConfig().getInt("premio"), "", "§fUltimo Vencedor: §e" + plugin.getConfig().getString("ultimo_vencedor"), ""));
                        skull.setItemMeta (metaskull);
                        inv.setItem(13, skull);

                        p.openInventory(inv);
                        return true;
                    }
                    if (!aberto) {
                        if (!iniciou) {
                            ItemStack skull = new ItemStack (Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal ());

                            SkullMeta metaskull = (SkullMeta) skull.getItemMeta ();
                            metaskull.setOwner (plugin.getConfig ().getString ("ultimo_vencedor"));
                            metaskull.setDisplayName ("§aInformações do Evento");
                            metaskull.setLore (Arrays.asList ("", "§7Status: §eNão ocorrendo", "", "§7Premiação: §eR$ " + plugin.getConfig ().getInt ("premio"), "", "§7Ultimo Vencedor: §e" + plugin.getConfig ().getString ("ultimo_vencedor"), ""));
                            skull.setItemMeta (metaskull);
                            inv.setItem (13, skull);

                            p.openInventory (inv);
                            return true;
                        }
                        return true;
                    }

                    return true;
                }
                //fim do /guerreiro info


                //inicio /guerreiro reload
                if (e.equalsIgnoreCase("reload")){
                    if (p.hasPermission ("lguerreiro.admin")){
                        p.sendMessage ("§aO plugin LGuerreiro reiniciou suas configurações");
                        plugin.reloadConfig();
                        return true;
                    }
                }
                //fim /guerreiro reload


                //inicio /guerreiro entrar
                if (e.equalsIgnoreCase("entrar")){
                    if (!iniciou) {
                        if (aberto) {
                            if (!onevent.contains (p.getName ())) {
                                p.sendMessage ("");
                                p.sendMessage ("§eVocê entrou no evento Guerreiro!");
                                p.sendMessage ("§ePara sair digite: /guerreiro sair");
                                p.sendMessage ("");
                                p.getWorld().playSound(p.getLocation(), Sound.DOOR_OPEN, 5, 1);
                                j = j + 1;
                                //p.teleport ()
                                onevent.add (p.getName());
                                for (Player all : Bukkit.getOnlinePlayers()){
                                    if (onevent.contains ((all))){
                                        all.sendMessage("§e[Guerreiro] " + j + " jogadores participando");

                                    }
                                }

                            }
                        } else {
                            p.sendMessage ("§cNão há nenhum evento Guerreiro ocorrendo no momento.");
                            p.getWorld().playSound(p.getLocation(), Sound.CAT_MEOW, 5, 1);
                        }
                    } else {
                        p.sendMessage ("§cDesculpe, o evento já foi iniciado, para verificar o status, utilize: /guerreiro info");
                        p.getWorld().playSound(p.getLocation(), Sound.CAT_MEOW, 5, 1);
                    }
                }
                //fim /guerreiro entrar


                //inicio /guerreiro sair
                if (e.equalsIgnoreCase("sair")) {
                    if (onevent.contains (p.getName ())){
                        if (!iniciou){
                            //p.teleport(spawn);
                            onevent.remove(p.getName());
                            p.sendMessage("");
                            p.sendMessage("§cVocê saiu do evento Guerreiro");
                            p.sendMessage("");

                            j = j - 1;

                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (onevent.contains(all.getName())) {
                                    all.sendMessage("§e[Guerreiro] " + j + " jogadores participando, " + p.getName () + " saiu");
                                }
                            }
                        } else {
                            p.sendMessage ("§cO evento já começou, não é possivel sair.");
                        }
                    } else {
                        p.sendMessage ("§cVocê não está participando do evento Guerreiro");
                    }
                }
                //fim /guerreiro sair



                //inicio /guerreiro cancelar
                if (e.equalsIgnoreCase("cancelar")) {
                    if (p.hasPermission ("lguerreiro.admin")){
                        if (!iniciou){
                            if (aberto){
                                plugin.getConfig ().getString("mensagem_de_cancelamentostaff").replaceAll ("%premio", String.valueOf (Integer.valueOf (premio)));
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (onevent.contains(all.getName())) {
                                        //all.teleport(spawn);
                                        onevent.remove(all.getName());
                                    }
                                }
                            } else {
                                p.sendMessage ("§cErro. Não há evento ocorrendo!");
                            }
                        } else {
                            p.sendMessage ("§cErro. O evento já iniciou.");
                        }
                    } else {
                        p.sendMessage ("§cErro. Permissão insuficiente!");
                    }
                }
                //terminar isso aq









            }
        }

        return false;
    }
}
