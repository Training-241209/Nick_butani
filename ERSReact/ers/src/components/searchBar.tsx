import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";

interface SearchBarProps {
  onSearch: (searchTerm: string) => void;
  onNewClick: () => void;
}

export function SearchBar({ onSearch, onNewClick }: SearchBarProps) {
  return (
    <div className="flex items-center gap-4 mb-6">
      <div className="flex-1">
        <Input
          placeholder="Search by description..."
          onChange={(e) => onSearch(e.target.value)}
          className="max-w-sm"
        />
      </div>
      <Button onClick={onNewClick}>
        <Plus className="mr-2 h-4 w-4" /> New Reimbursement
      </Button>
    </div>
  );
}